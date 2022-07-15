package com.vanmo.ioc.scopes

import com.vanmo.common.IDependency

trait MutableScope {
  def set[P, R](key: String, d: IDependency[P, R]): Unit
}
