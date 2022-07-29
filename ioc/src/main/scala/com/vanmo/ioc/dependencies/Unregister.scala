package com.vanmo.ioc.dependencies

import com.vanmo.common.{IDependency, Key}
import com.vanmo.ioc.errors.ResolveError
import com.vanmo.ioc.scopes.MutableScope
import com.vanmo.ioc.{CURRENT_SCOPE, resolve}

object Unregister extends IDependency[Null, Unregister.Command] {

  class Command(val scope: MutableScope) {
    def apply[P, R](k: Key[P, R]): Option[IDependency[P, R]] =
      scope.remove(k.toString)
  }

  override def apply(v1: Null): Command =
    resolve(CURRENT_SCOPE) match {
      case s: MutableScope => new Command(s)
      case _ =>
        throw new ResolveError(
          s"Current scope is immutable",
          None
        )
    }

}
