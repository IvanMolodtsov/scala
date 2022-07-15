package com.vanmo.common

import scala.util.Try

//type Key[P, R] = String
trait IDependency[P, R] extends (P => R)
//type KeyVal[P, R] = (Key[P, R], IDependency[P, R])
