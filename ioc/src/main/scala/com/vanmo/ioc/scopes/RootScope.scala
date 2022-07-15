package com.vanmo.ioc.scopes

import com.vanmo.common.{ IDependency, Key }
import com.vanmo.ioc.{
  dependencies,
  resolve,
  CURRENT_SCOPE,
  EXECUTE_IN_NEW_SCOPE,
  EXECUTE_IN_SCOPE,
  GlobalScope,
  NEW_SCOPE,
  REGISTER
}
import com.vanmo.ioc.dependencies.{ Execute, NewScope }
import com.vanmo.ioc.errors.ResolveError

import java.util.concurrent.ConcurrentHashMap
import scala.collection.{ concurrent, mutable }
import scala.util.{ Failure, Success, Try }

class RootScope extends IScope {

  private val dict: mutable.Map[String, IDependency[_, _]] = concurrent.TrieMap(
    REGISTER -> dependencies.Register,
    CURRENT_SCOPE -> { _ =>
      GlobalScope.current
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
      case None => throw new ResolveError(message = s"Dependency $key not found", cause = None)
}
