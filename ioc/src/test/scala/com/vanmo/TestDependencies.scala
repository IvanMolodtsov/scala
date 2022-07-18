package com.vanmo

import com.vanmo.common.IDependency

object TestDependencies {
  object SimpleDependency extends IDependency[String, Int] {
    override def apply(str: String): Int = str.length
  }
}
