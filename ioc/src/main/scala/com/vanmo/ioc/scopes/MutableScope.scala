package com.vanmo.ioc.scopes

import com.vanmo.common.IDependency
import com.vanmo.ioc.scopes.IScope

trait MutableScope extends IScope {

  def set[P, R](key: String, d: IDependency[P, R]): Unit

  def remove[P, R](key: String): Option[IDependency[P, R]]
}
