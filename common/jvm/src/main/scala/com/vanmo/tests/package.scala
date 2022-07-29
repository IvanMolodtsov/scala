package com.vanmo

import scala.concurrent.{duration, Await, Future, ExecutionContext}
import scala.util.Try

package object tests {

  val context: ExecutionContext = ExecutionContext.global

  def await[T](f: Future[T], e: Option[Try[T]] => Any) = {
    import duration._
    e(Some(Try(Await.result(f, 1.seconds))))
  }
}
