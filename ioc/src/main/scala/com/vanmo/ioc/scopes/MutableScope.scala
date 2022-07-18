package com.vanmo.ioc.scopes

import com.vanmo.common.IDependency

trait MutableScope extends IScope {
  def set[P, R](key: String, d: IDependency[P, R]): Unit

  def remove[P, R](key: String): Option[IDependency[P, R]]
}
