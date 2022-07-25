package com.vanmo.tests

import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.Future
import org.scalatest.BeforeAndAfter
import com.vanmo.ioc.scopes.IScope
import scala.concurrent.ExecutionContext

class asyncTest extends AnyFunSuite with BeforeAndAfter {
  import com.vanmo.ioc._
  implicit val ec: ExecutionContext = resolve(EXECUTION_CONTEXT, context)

  test("Futures execute in a separate scope") {
    val gScope = resolve(CURRENT_SCOPE)
    val t =
      Future {
        val scope = resolve(CURRENT_SCOPE)
        assert(scope !== gScope)
      }

    await(
      t, {
        val scope = resolve(CURRENT_SCOPE)
        assert(scope === gScope)
      }
    )
  }
}
