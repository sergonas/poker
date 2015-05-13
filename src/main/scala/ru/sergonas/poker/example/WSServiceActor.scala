package ru.sergonas.poker.example

import akka.actor._
import spray.json._
import ru.sergonas.poker.messaging.GameJsonProtocol._
import spray.can.Http
import spray.can.websocket.WebSocketServerWorker
import spray.can.websocket.frame.TextFrame
import spray.routing.HttpServiceActor

class WSServiceWorkerActor(val serverConnection: ActorRef) extends HttpServiceActor with WebSocketServerWorker  {
  override def businessLogic = {
    case x: TextFrame =>
      val frameStringValue = x.payload.utf8String
      log.info("Receives some: {}", frameStringValue)
      sender() ! x
    case x => log.info("Received message: {}", x)
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