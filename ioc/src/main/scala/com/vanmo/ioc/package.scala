package com.vanmo

import scala.concurrent.Future

import com.vanmo.common.{ Executable, IDependency, Key, RegisterCommand, UnregisterCommand }
import com.vanmo.ioc.dependencies.{ Execute, Register, Unregister }
import com.vanmo.ioc.scopes.{ IScope, RootScope, Scope }

/** Package for IoC methods and basic dependencies
  * {{{
  * import com.vanmo.ioc._
  *
  * resolve(REGISTER)(KEY, DEPENDENCY)
  * }}}
  */
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

  /** Registers a dependency in current scope
    * @return
    *   [[com.vanmo.common.RegisterCommand]]
    */
  object REGISTER extends Key[Null, RegisterCommand]("IoC.REGISTER")

  /** Removes dependency from current scope
    * @return
    *   [[com.vanmo.common.UnregisterCommand]]
    */
  object UNREGISTER extends Key[Null, UnregisterCommand]("IoC.Unregister")

  /** resolve root scope
    * @return
    *   [[com.vanmo.ioc.scopes.IScope]]
    */
  object ROOT_SCOPE extends Key[Null, IScope]("IoC.ROOT_SCOPE")

  /** resolve current scope
    * @return
    *   [[com.vanmo.ioc.scopes.IScope]]
    */
  object CURRENT_SCOPE extends Key[Null, IScope]("IoC.CURRENT_SCOPE")

  /** Set scope as current
    * @return
    *   [[Unit]]
    */
  object SET_SCOPE extends Key[IScope, Unit]("IoC.SET_SCOPE")

  /** resolve a new IScope
    * @return
    *   [[com.vanmo.ioc.scopes.IScope]]
    */
  object NEW_SCOPE extends Key[Option[IScope], IScope]("Scope.NEW")

  /** Executes [[com.vanmo.common.Executable]] in a specified scope
    * @return
    *   [[com.vanmo.common.Executable]]
    */
  object EXECUTE_IN_SCOPE extends Key[IScope, Executable]("IoC.EXECUTE_IN_SCOPE")

  /** Executes [[com.vanmo.common.Executable]] in a new scope extended from current scope
    * @return
    *   [[com.vanmo.common.Executable]]
    */
  object EXECUTE_IN_NEW_SCOPE extends Key[Null, Executable]("IoC.EXECUTE_IN_NEW_SCOPE")

  /** Extends [[scala.concurrent.ExecutionContext]] with information about scopes
    * @return
    *   [[scala.concurrent.ExecutionContext]]
    */
  object EXECUTION_CONTEXT extends Key[ExecutionContext, ExecutionContext]("IoC.EXECUTION_CONTEXT")

  /** Resolves a dependency with arguments
    *
    * @param key
    *   Dependency key
    * @param args
    *   Dependency arguments
    * @return
    *   Dependency execution result
    * @tparam P
    *   dependency arguments type
    * @tparam R
    *   Dependency execution result
    */
  final def resolve[P, R](key: Key[P, R], args: P): R =
    GlobalScope.current.get[P, R](key.toString)(args)

  /** Resolves a dependency without arguments
    *
    * @param key
    *   Dependency key
    * @param args
    *   Dependency arguments
    * @return
    *   Dependency execution result
    * @tparam R
    *   Dependency execution result
    */
  final def resolve[R](key: Key[Null, R]): R =
    resolve[Null, R](key, null)
}
