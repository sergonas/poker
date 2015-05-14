package ru.sergonas.poker.example

import akka.actor._
import akka.util.ByteString
import ru.sergonas.poker.messaging.{Player, InputEvents}

import ru.sergonas.poker.messaging.GameJsonProtocol._
import spray.can.Http
import spray.can.websocket.WebSocketServerWorker
import spray.can.websocket.frame.TextFrame
import spray.routing.HttpServiceActor

class WSServiceWorkerActor(val serverConnection: ActorRef) extends HttpServiceActor with WebSocketServerWorker  {
  val player = context.actorOf(Props[Player])
  override def businessLogic = {
    case x: TextFrame =>
      val frameStringValue = x.payload.utf8String
      val eventOrFail = InputEvents.tryParseEvent(frameStringValue)
      eventOrFail match {
        case Left(event) => player ! event
        case Right(fail) =>
          sender() ! TextFrame(fail)
          log.warning(s"Error while processing received command: $fail}")
      }
//    case x: akka.io.Tcp.Closed => self ! PoisonPill
    case x => log.info(s"Received message: ${x.getClass.getName} - $x")
  }
}

object WSServiceActor {
  def props() = Props(classOf[WSServiceActor])
}

class WSServiceActor extends Actor with ActorLogging {
  def receive = {
    case Http.Connected(remoteAddress, localAddress) =>
      val serverConnection = sender()
      val conn = context.actorOf(Props(classOf[WSServiceWorkerActor], serverConnection))
      serverConnection ! Http.Register(conn)
  }
}