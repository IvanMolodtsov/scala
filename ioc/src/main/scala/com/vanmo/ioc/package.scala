package com.vanmo

import com.vanmo.common.{ IDependency, Key }
import com.vanmo.ioc.dependencies.{ Execute, Register, Unregister }
import com.vanmo.ioc.scopes.{ IScope, RootScope, Scope }

package object ioc {

  import scala.concurrent.ExecutionContext
  import scala.util.{ DynamicVariable, Try }

  private[ioc] object GlobalScope {
    val root: RootScope = new RootScope()

    val _current = new DynamicVariable[Option[IScope]](None)

    def current: IScope =
      _current.value match {

        case Some(scope) =>
          scope

        case None =>
          _current.value = Some(new Scope(root))
          _current.value.get
      }

    def current_=(value: IScope): Unit =
      _current.value = Some(value)
  }

  object REGISTER extends Key[Null, Register.Command]("IoC.REGISTER")
  object UNREGISTER extends Key[Null, Unregister.Command]("IoC.Unregister")
  object ROOT_SCOPE extends Key[Null, IScope]("IoC.ROOT_SCOPE")
  object CURRENT_SCOPE extends Key[Null, IScope]("IoC.CURRENT_SCOPE")
  object SET_SCOPE extends Key[IScope, Unit]("IoC.SET_SCOPE")
  object NEW_SCOPE extends Key[Option[IScope], IScope]("Scope.NEW")
  object EXECUTE_IN_SCOPE extends Key[IScope, Execute.ScopeGuard]("IoC.EXECUTE_IN_SCOPE")
  object EXECUTE_IN_NEW_SCOPE extends Key[Null, Execute.ScopeGuard]("IoC.EXECUTE_IN_NEW_SCOPE")

  extension (sc: StringContext) {
    def k(): Key[Any, Any] = new Key[Any, Any](sc.toString) {}
  }

  def inject[P, R](key: Key[P, R]): IDependency[P, R] =
    GlobalScope.current.get[P, R](key.toString)

  final def resolve[P, R](key: Key[P, R], args: P): R =
    GlobalScope.current.get[P, R](key.toString)(args)

  final def resolve[R](key: Key[Null, R]): R =
    resolve[Null, R](key, null)
}
