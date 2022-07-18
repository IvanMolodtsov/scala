package com.vanmo

import org.scalatest.funspec.AsyncFunSpec
import scala.concurrent.{ Await, Future }

class asyncTest extends AsyncFunSpec {
  import com.vanmo.ioc._
  it("async test") {
    val gScope = resolve(CURRENT_SCOPE)
    println(gScope)
    val s = resolve(NEW_SCOPE, None)

    val asserts = Future {
      resolve(SET_SCOPE, s)
      resolve(REGISTER)(
        TestKeys.SimpleKey,
        TestDependencies.SimpleDependency
      )
      resolve(TestKeys.SimpleKey, "42")
    }.map { _ =>
      val scope = resolve(CURRENT_SCOPE)
      assert(scope !== gScope)

    }
    val scope = resolve(CURRENT_SCOPE)
    assert(scope == gScope)

    assertThrows[errors.ResolveError] {
      println(resolve(TestKeys.SimpleKey, "42"))
    }
    asserts
  }
}
