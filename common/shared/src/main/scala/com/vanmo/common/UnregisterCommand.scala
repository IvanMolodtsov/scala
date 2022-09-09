package com.vanmo.common

trait UnregisterCommand {

  /** Remove dependency from a specified scope
    *
    * @param key
    *   key of the dependency
    * @return
    *   removed dependency
    * @tparam P
    *   parameters
    * @tparam R
    *   dependency return types
    */
  def apply[P, R](k: Key[P, R]): Option[IDependency[P, R]]
}
