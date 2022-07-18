package com.vanmo
import com.vanmo.ioc.errors.ResolveError
import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.duration._

import scala.concurrent.{ Await, Future }
import scala.util.Try

class resolveTest extends AnyFunSuite {
  import com.vanmo.ioc._

  test("Register\\Unregister works") {
    resolve(REGISTER)(
      TestKeys.SimpleKey,
      TestDependencies.SimpleDependency
    )
    val result = resolve(TestKeys.SimpleKey, "42")

    assert(result == 2)

    resolve(UNREGISTER)(TestKeys.SimpleKey)

    assertThrows[ResolveError] {
      println(resolve(TestKeys.SimpleKey, "42"))
    }
  }

  test("Execute in scope works") {
    val gScope = resolve(CURRENT_SCOPE)

    resolve(EXECUTE_IN_NEW_SCOPE) { () =>
      assert(gScope != resolve(CURRENT_SCOPE))
      resolve(REGISTER)(
        TestKeys.SimpleKey,
        TestDependencies.SimpleDependency
      )
      val result = resolve(TestKeys.SimpleKey, "42")
      assert(result == 2)
      Try(result)
    }

    assert(resolve(CURRENT_SCOPE) == gScope)
    assertThrows[ResolveError] {
      println(resolve(TestKeys.SimpleKey, "42"))
    }
  }

  test("Execute in scope error pass through") {
    val gScope = resolve(CURRENT_SCOPE)

    val res = resolve(EXECUTE_IN_NEW_SCOPE) { () =>
      assert(gScope != resolve(CURRENT_SCOPE))
      throw new Error("Exec error")
    }

    assert(resolve(CURRENT_SCOPE) == gScope)
    assert(res.isFailure)
  }

  test("Async operations in separate scope") {
    import scala.concurrent.ExecutionContext.Implicits.global
    val gScope = resolve(CURRENT_SCOPE)
    println(gScope)

    val scope = Await.result(
      Future {
        resolve(CURRENT_SCOPE)
      },
      1.seconds
    )

    assert(scope != gScope)
  }

}
