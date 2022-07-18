package com.vanmo.common

import scala.collection.{ concurrent, mutable }

object Store {
  def apply(deps: (String, IDependency[_, _])*): mutable.Map[String, IDependency[_, _]] = concurrent.TrieMap(deps: _*)
}
