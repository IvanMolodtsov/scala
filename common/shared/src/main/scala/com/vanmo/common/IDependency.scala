package com.vanmo.common

trait IDependency[P, R] extends (P => R)
