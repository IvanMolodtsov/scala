package com.vanmo.common

import scala.annotation.targetName

trait Key[P, R](name: String) {
  override def toString: String = name

  @targetName("IDependency")
  def ->(dep: IDependency[P, R]): (String, IDependency[P, R]) = (name, dep)
}
