package com.vanmo.ioc.scopes

import com.vanmo.common.{ IDependency, Key }
import com.vanmo.ioc.errors.ResolveError

import scala.collection.{ concurrent, mutable }
import scala.util.{ Failure, Success, Try }

class Scope(private val parent: IScope) extends IScope, MutableScope {

  private val dict: mutable.Map[String, IDependency[_, _]] = concurrent.TrieMap()

  override def get[P, R](key: String): IDependency[P, R] =
    dict.get(key) match
      case Some(dep) => dep.asInstanceOf[IDependency[P, R]]
      case None => parent.get[P, R](key)

  override def set[P, R](key: String, d: IDependency[P, R]): Unit =
    dict.addOne(key -> d)

}
