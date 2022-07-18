package com.vanmo.ioc.dependencies

import com.vanmo.common.{ IDependency, Key }
import com.vanmo.ioc.errors.ResolveError
import com.vanmo.ioc.{ resolve, CURRENT_SCOPE }
import com.vanmo.ioc.scopes.MutableScope

import scala.util.{ Failure, Success, Try }

object Register extends IDependency[Null, Register.Command] {

  class Command(val scope: MutableScope) {
    def apply[P, R](k: Key[P, R], d: IDependency[P, R]): Unit =
      scope.set(k.toString, d)
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
