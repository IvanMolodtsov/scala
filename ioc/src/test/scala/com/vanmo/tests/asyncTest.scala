package com.vanmo.tests

import scala.concurrent.{Future, ExecutionContext}
import scala.util.Failure

import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

import com.vanmo.ioc.scopes.IScope

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
      t,
      { _ =>
        val scope = resolve(CURRENT_SCOPE)
        assert(scope === gScope)
      }
    )
  }

  test("Future fail doesn't pollute global scope") {
    val gScope = resolve(CURRENT_SCOPE)
    val t =
      Future {
        val scope = resolve(CURRENT_SCOPE)
        assert(scope !== gScope)
        throw Error("Future failed")
      }

    await(
      t,
      { res =>
        val scope = resolve(CURRENT_SCOPE)
        assert(scope === gScope)

        res match {
          case Some(Failure(ex)) => assert(true)
          case _ => assert(false)
        }
      }
    )
  }
}
