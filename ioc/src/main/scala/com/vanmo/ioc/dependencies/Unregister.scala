package com.vanmo.ioc.dependencies

import com.vanmo.common.{ IDependency, Key, UnregisterCommand }
import com.vanmo.ioc.errors.ResolveError
import com.vanmo.ioc.scopes.MutableScope
import com.vanmo.ioc.{ resolve, CURRENT_SCOPE }

object Unregister extends IDependency[Null, UnregisterCommand] {

  class Command(val scope: MutableScope) extends UnregisterCommand {
    def apply[P, R](k: Key[P, R]): Option[IDependency[P, R]] =
      scope.remove(k.toString)
  }

  override def apply(v1: Null): UnregisterCommand =
    resolve(CURRENT_SCOPE) match {
      case s: MutableScope => new Command(s)
      case _ =>
        throw new ResolveError(
          s"Current scope is immutable",
          None
        )
    }

}
