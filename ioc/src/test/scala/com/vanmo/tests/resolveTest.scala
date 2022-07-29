package com.vanmo.tests

import scala.util.Try

import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite

class resolveTest extends AnyFunSuite with BeforeAndAfterEach {
  import com.vanmo.ioc._

  test("Register\\Unregister works") {
    resolve(REGISTER)(
      TestKeys.SimpleKey,
      TestDependencies.SimpleDependency
    )
    val result = resolve(TestKeys.SimpleKey, "42")

    assert(result == 2)

    resolve(UNREGISTER)(TestKeys.SimpleKey)

    assertThrows[errors.ResolveError] {
      println(resolve(TestKeys.SimpleKey, "42"))
    }
  }

  test("Execute in scope works") {
    val gScope = resolve(CURRENT_SCOPE)

    resolve(EXECUTE_IN_NEW_SCOPE) {
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
    assertThrows[errors.ResolveError] {
      println(resolve(TestKeys.SimpleKey, "42"))
    }
  }

  test("Execute in scope error pass through") {
    val gScope = resolve(CURRENT_SCOPE)

    val res = resolve(EXECUTE_IN_NEW_SCOPE) {
      assert(gScope != resolve(CURRENT_SCOPE))
      throw new Error("Exec error")
    }

    assert(resolve(CURRENT_SCOPE) == gScope)
    assert(res.isFailure)
  }
}
