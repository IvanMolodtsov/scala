package com.vanmo.common

/** Common dependency interface.
  * @tparam P
  *   arguments of the dependency
  * @tparam R
  *   return type of the dependency
  */
trait IDependency[P, R] extends (P => R)
