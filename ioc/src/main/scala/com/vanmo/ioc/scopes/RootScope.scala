package com.vanmo.ioc.scopes

import com.vanmo.common.{IDependency, Store}
import com.vanmo.ioc.errors.ResolveError
import com.vanmo.ioc.dependencies.{Execute, Register, Unregister, NewScope}
import com.vanmo.ioc.{ resolve, GlobalScope,
  CURRENT_SCOPE, EXECUTE_IN_NEW_SCOPE, EXECUTE_IN_SCOPE, NEW_SCOPE, REGISTER, ROOT_SCOPE, SET_SCOPE, UNREGISTER
}

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

class RootScope extends IScope {

  private val dict: mutable.Map[String, IDependency[_, _]] = Store(
    REGISTER -> Register,
    UNREGISTER -> Unregister,
    ROOT_SCOPE -> { _ =>
      GlobalScope.root
    },
    CURRENT_SCOPE -> { _ =>
      GlobalScope.current
    },
    SET_SCOPE -> { scope =>
      GlobalScope.current = scope
    },
    NEW_SCOPE -> NewScope,
    EXECUTE_IN_SCOPE -> Execute,
    EXECUTE_IN_NEW_SCOPE -> { _ =>
      val scope = resolve(NEW_SCOPE, None)
      resolve(EXECUTE_IN_SCOPE, scope)
    }
  )

  override def get[P, R](key: String): IDependency[P, R] =
    dict.get(key) match
      case Some(dep) => dep.asInstanceOf[IDependency[P, R]]
      case None => throw new ResolveError(message = s"Dependency $key not found")
}
