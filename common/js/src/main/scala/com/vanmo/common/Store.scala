package com.vanmo.common

import scala.collection.mutable

object Store {
  def apply(deps: (String, IDependency[_, _])*): mutable.Map[String, IDependency[_, _]] = mutable.Map(deps: _*)
}
