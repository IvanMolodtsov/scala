package com.vanmo.common

import scala.util.Try

trait Executable {
  def apply[T](f: => T): Try[T]
}
