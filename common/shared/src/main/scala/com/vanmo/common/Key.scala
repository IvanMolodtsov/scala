package com.vanmo.common

import scala.annotation.targetName

/** Key for resolving dependencies
  * @param name
  * @tparam P
  *   arguments of the dependency
  * @tparam R
  *   return type of the dependency
  */
trait Key[P, R](name: String) {
  override def toString: String = name

  @targetName("IDependency")
  def ->(dep: IDependency[P, R]): (String, IDependency[P, R]) = (name, dep)
}
