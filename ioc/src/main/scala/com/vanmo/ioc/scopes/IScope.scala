package com.vanmo.ioc.scopes

import com.vanmo.common.IDependency

/** Dependency scope interface */
trait IScope {
  def get[P, R](key: String): IDependency[P, R]
}
