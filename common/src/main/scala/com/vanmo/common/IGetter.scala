package com.vanmo.common

trait IGetter {
  def get[T](key: String, or: () => T = () => None): Option[T]
}
