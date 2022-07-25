package com.vanmo

import scala.concurrent.Future
import scalajs.js
import scala.concurrent.ExecutionContext

package object tests {
  import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits

  val context: ExecutionContext = Implicits.global

  def await(f: Future[Any], e: => Any) =
    js.timers.setTimeout(1) {
      if (f.isCompleted) {
        e
      } else {
        throw new Error("await failed")
      }
    }
}
