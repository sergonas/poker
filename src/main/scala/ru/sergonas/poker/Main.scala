package ru.sergonas.poker

import akka.actor._
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import ru.sergonas.poker.example.{DemoServiceActor, DemoService}
import spray.can.Http
import scala.concurrent.Await
import scala.concurrent.duration._
import ru.sergonas.poker.materials.Cards._
import ru.sergonas.poker.materials.{Quit, Player}
import scala.language.postfixOps

/**
 * Created by Serega on 28.01.2015.
 */
object Main extends App {
/*  implicit val timeout = Timeout(5 seconds)

  val s10 = Card(Suit.Spades, Rank.Ten)
  val cAce = Card(Suit.Clubs, Rank.Ace)

  val system = ActorSystem("MySystem")
  val player = system.actorOf(Props[Player], name = "player")
  player ! CardPair(s10, cAce)
  val response = player ? Quit()
  val res = Await.result(response, timeout.duration).asInstanceOf[String]
  println(res)*/

  implicit val system = ActorSystem()

  // the handler actor replies to incoming HttpRequests
  val handler = system.actorOf(Props[DemoServiceActor], name = "handler")

  IO(Http) ! Http.Bind(handler, interface = "localhost", port = 8080)
}

