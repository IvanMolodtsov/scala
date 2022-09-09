package com.vanmo.common

trait RegisterCommand {

  /** Register dependency in a specified scope
    *
    * @param key
    *   key of the dependency
    * @param dependency
    *   dependency class, object or arrow function
    * @tparam P
    *   parameters
    * @tparam R
    *   dependency return types
    */
  def apply[P, R](key: Key[P, R], dependency: IDependency[P, R]): Unit
}
