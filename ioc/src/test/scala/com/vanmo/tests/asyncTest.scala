package com.vanmo.tests

import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.Future
import org.scalatest.BeforeAndAfter
import com.vanmo.ioc.scopes.IScope

class asyncTest extends AnyFunSuite with BeforeAndAfter {
  import com.vanmo.ioc._

  test("async test") {

    val gScope = resolve(CURRENT_SCOPE)

    val s = resolve(NEW_SCOPE, None)

    val t = Future {
      resolve(EXECUTE_IN_SCOPE, s) { () =>
        resolve(REGISTER)(
          TestKeys.SimpleKey,
          TestDependencies.SimpleDependency
        )
        resolve(TestKeys.SimpleKey, "42")
        val scope = resolve(CURRENT_SCOPE)
        assert(scope !== gScope)
      }
    }
    await(
      t,
      { () =>
        val scope = resolve(CURRENT_SCOPE)
        assert(scope == gScope)

        assertThrows[errors.ResolveError] {
          println(resolve(TestKeys.SimpleKey, "42"))
        }
      }
    )
  }
}
