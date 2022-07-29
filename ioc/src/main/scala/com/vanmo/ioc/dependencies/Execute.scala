package com.vanmo.ioc.dependencies

import scala.util.{ Failure, Success, Try }

import com.vanmo.common.IDependency
import com.vanmo.ioc.scopes.IScope
import com.vanmo.ioc.{CURRENT_SCOPE, GlobalScope, SET_SCOPE, resolve}
import com.vanmo.tests.context

object Execute extends IDependency[IScope, Execute.ScopeGuard] {
  class ScopeGuard(scope: IScope, originalScope: IScope) {

    def apply[T](f: => T): Try[T] =
      GlobalScope._current.withValue(Some(scope)) {
        Try(f)
      }

  }

  override def apply(scope: IScope): ScopeGuard = new ScopeGuard(scope, resolve(CURRENT_SCOPE))
}
