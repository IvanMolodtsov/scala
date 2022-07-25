package com.vanmo.ioc.dependencies

import com.vanmo.common.IDependency
import scala.concurrent.ExecutionContext
import com.vanmo.ioc.{ resolve, CURRENT_SCOPE, EXECUTE_IN_SCOPE, NEW_SCOPE }
import scala.util.{ Failure, Success }
import com.vanmo.ioc.scopes.IScope

object ExecutionScope extends IDependency[ExecutionContext, ExecutionContext] {

  class ExecutionContextWithScope(ec: ExecutionContext, scope: IScope) extends ExecutionContext {
    override def execute(runnable: Runnable): Unit = {
      val newScope = resolve(NEW_SCOPE, Some(scope))
      ec.execute(new Runnable {
        override def run(): Unit = resolve(EXECUTE_IN_SCOPE, newScope) {
          runnable.run()
        } match {
          case Failure(ex) => throw ex
          case Success(res) => res
        }
      })

    }

    override def reportFailure(cause: Throwable): Unit = ec.reportFailure(cause)
  }
  override def apply(ec: ExecutionContext): ExecutionContext = {
    val scope = resolve(CURRENT_SCOPE)
    new ExecutionContextWithScope(ec, scope)
  }
}
