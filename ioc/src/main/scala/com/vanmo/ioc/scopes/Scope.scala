package com.vanmo.ioc.scopes

import com.vanmo.common.{IDependency, Store}

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

class Scope(private val parent: IScope) extends MutableScope {

  private val dict: mutable.Map[String, IDependency[_, _]] = Store()

  override def get[P, R](key: String): IDependency[P, R] =
    dict.get(key) match
      case Some(dep) => dep.asInstanceOf[IDependency[P, R]]
      case None => parent.get[P, R](key)

  override def set[P, R](key: String, d: IDependency[P, R]): Unit =
    dict.addOne(key -> d)

  override def remove[P, R](key: String): Option[IDependency[P, R]] =
    dict.remove(key) match
      case Some(dep) => Some(dep.asInstanceOf[IDependency[P, R]])
      case None => None
}
