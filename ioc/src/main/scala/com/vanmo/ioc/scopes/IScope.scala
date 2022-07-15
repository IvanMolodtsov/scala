package com.vanmo.ioc.scopes

import com.vanmo.common.IDependency

import scala.util.Try

trait IScope {
  def get[P, R](key: String): IDependency[P, R]
}
