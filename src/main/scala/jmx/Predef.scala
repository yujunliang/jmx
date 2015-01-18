package jmx

import akka.actor.ActorContext
import akka.util.Timeout

import scala.concurrent.duration._

/**
 * Some useful variables to share with actors without cluttering them, these definitions make
 * the code shorter.
 */
trait Predef {

  implicit val context: ActorContext
  implicit val executor = context.dispatcher
  val scheduler         = context.system.scheduler
  val eventStream       = context.system.eventStream
  val config            = context.system.settings.config
  val stateTimeout      = 5.seconds
  implicit val timeout  = Timeout(6 seconds)

}