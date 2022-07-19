package com.vanmo.ioc.dependencies

import com.vanmo.common.IDependency
import com.vanmo.ioc.{CURRENT_SCOPE, SET_SCOPE, resolve}
import com.vanmo.ioc.scopes.IScope

import scala.util.{Failure, Success, Try}

object Execute extends IDependency[IScope, Execute.ScopeGuard] {
  class ScopeGuard(scope: IScope, originalScope: IScope) {

    def apply[T](f: () => T): Try[T] = {
      resolve(SET_SCOPE, scope)
      val res = Try(f())
      resolve(SET_SCOPE, originalScope)
      res
    }

  }

  override def apply(scope: IScope): ScopeGuard = new ScopeGuard(scope, resolve(CURRENT_SCOPE))
}
