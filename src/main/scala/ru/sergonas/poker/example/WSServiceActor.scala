package ru.sergonas.poker.example

import akka.actor._
import spray.can.Http
import spray.can.websocket.WebSocketServerWorker
import spray.can.websocket.frame.TextFrame
import spray.routing.HttpServiceActor

class WSServiceWorkerActor(val serverConnection: ActorRef) extends HttpServiceActor with WebSocketServerWorker  {
  override def businessLogic = {
    case x: TextFrame =>
      log.info("Receives some: {}", x)
      sender() ! x
    case x => log.info("Received message: {}", x)
  }

  def businessLogicNoUpgrade: Receive = {
    implicit val refFactory: ActorRefFactory = context
    runRoute {
      getFromResourceDirectory("webapp")
    }
  }

  override def receive = handshaking orElse businessLogicNoUpgrade orElse closeLogic
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