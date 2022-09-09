package com.vanmo.ioc.dependencies

import scala.util.{ Failure, Success, Try }

import com.vanmo.common.{ IDependency, Key, RegisterCommand }
import com.vanmo.ioc.errors.ResolveError
import com.vanmo.ioc.scopes.MutableScope
import com.vanmo.ioc.{ resolve, CURRENT_SCOPE }

object Register extends IDependency[Null, RegisterCommand] {

  class Command(val scope: MutableScope) extends RegisterCommand {
    override def apply[P, R](k: Key[P, R], d: IDependency[P, R]): Unit =
      scope.set(k.toString, d)
  }

  override def apply(v1: Null): RegisterCommand =
    resolve(CURRENT_SCOPE) match {
      case s: MutableScope => new Command(s)
      case _ =>
        throw new ResolveError(
          s"Current scope is immutable",
          None
        )
    }
}
