package ru.sergonas.poker

import akka.actor._
import akka.event.Logging
import akka.io.IO
import ru.sergonas.poker.example.{DemoServiceActor, WSServiceActor}
import spray.can.Http
import spray.can.server.UHttp

import scala.language.postfixOps

object Main extends App {
  implicit val system = ActorSystem()

  // the handler actor replies to incoming HttpRequests
  val handler = system.actorOf(Props[DemoServiceActor], name = "handler")
  val wsService = system.actorOf(Props[WSServiceActor], name = "myWsService")

  IO(UHttp) ! Http.Bind(wsService, interface = "localhost", port = 8483)
  system.actorSelection("/user/IO-HTTP") ! PoisonPill
  IO(Http) ! Http.Bind(handler, interface = "localhost", port = 8080)
  sys.addShutdownHook({ IO(UHttp) ! Http.Unbind; IO(Http) ! Http.Unbind; system.shutdown() })
}