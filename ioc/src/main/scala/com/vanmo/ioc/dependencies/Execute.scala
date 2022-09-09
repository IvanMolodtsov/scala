package com.vanmo.ioc.dependencies

import scala.util.{ Failure, Success, Try }

import com.vanmo.common.{ Executable, IDependency }
import com.vanmo.ioc.scopes.IScope
import com.vanmo.ioc.{ resolve, CURRENT_SCOPE, GlobalScope, SET_SCOPE }
import com.vanmo.tests.context

object Execute extends IDependency[IScope, Executable] {
  class ScopeGuard(scope: IScope, originalScope: IScope) extends Executable {

    def apply[T](f: => T): Try[T] =
      GlobalScope._current.withValue(Some(scope)) {
        Try(f)
      }

  }

  override def apply(scope: IScope): ScopeGuard = new ScopeGuard(scope, resolve(CURRENT_SCOPE))
}
