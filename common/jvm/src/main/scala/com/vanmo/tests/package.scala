package com.vanmo

import scala.concurrent.{ duration, Await, Future }
import scala.concurrent.ExecutionContext

package object tests {

  val context: ExecutionContext = ExecutionContext.global

  def await(f: Future[Any], e: => Any) = {
    import duration._
    Await.result(f, 10.seconds)
    e
  }

}
