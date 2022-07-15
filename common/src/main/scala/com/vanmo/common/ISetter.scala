package com.vanmo.common

trait ISetter {
  def set[T](key: String, value: T): Unit
}
