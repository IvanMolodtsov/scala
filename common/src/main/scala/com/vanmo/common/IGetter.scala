package com.vanmo.common

trait IGetter {
  def get[T](key: String, or: () => T = () => throw new Error("Not Found")): T
}
