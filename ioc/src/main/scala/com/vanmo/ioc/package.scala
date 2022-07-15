package com.vanmo

import com.vanmo.common.{ IDependency, Key }
import com.vanmo.ioc.scopes.{ IScope, RootScope, Scope }
import com.vanmo.ioc.dependencies.{ Execute, Register }

import scala.util.Try

package object ioc {

  private[ioc] object GlobalScope {
    val root: RootScope = new RootScope()
    //  var current: ThreadLocal[IScope] = ThreadLocal.withInitial[IScope](() => new Scope(root))
    var current = new Scope(root)
  }

  object REGISTER extends Key[Null, Register.Command]("IoC.REGISTER")
  object CURRENT_SCOPE extends Key[Null, IScope]("IoC.CURRENT_SCOPE")
  object SET_SCOPE extends Key[IScope, Unit]("IoC.SET_SCOPE")
  object NEW_SCOPE extends Key[Option[IScope], IScope]("Scope.NEW")
  object EXECUTE_IN_SCOPE extends Key[IScope, Execute.ScopeGuard]("IoC.EXECUTE_IN_SCOPE")
  object EXECUTE_IN_NEW_SCOPE extends Key[IScope, Execute.ScopeGuard]("IoC.EXECUTE_IN_NEW_SCOPE")

  def inject[P, R](key: Key[P, R]): IDependency[P, R] =
    GlobalScope.current.get[P, R](key.toString)

  final def resolve[P, R](key: Key[P, R], args: P): R =
    GlobalScope.current.get[P, R](key.toString)(args)

  final def resolve[R](key: Key[Null, R]): R =
    resolve[Null, R](key, null)
}
