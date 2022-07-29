package com.vanmo

import scala.concurrent.{Future, ExecutionContext}
import scala.util.Try

import scalajs.js

package object tests {
  import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits

  val context: ExecutionContext = Implicits.global

  def await[T](f: Future[T], e: Option[Try[T]] => Any) =
    js.timers.setTimeout(1) {
      if (f.isCompleted) {
        e(f.value)
      } else {
        throw new Error("await failed")
      }
    }
}
