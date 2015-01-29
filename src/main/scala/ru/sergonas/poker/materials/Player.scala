package ru.sergonas.poker.materials

import akka.actor._
import ru.sergonas.poker.materials.Cards.{CardPair, Card}

class Player extends Actor {
  def actorRefFactory = context

  var _hand : CardPair = null
  def hand = _hand
  def hand_= (pair: CardPair) = _hand = pair

  override def receive: Receive = {
    case h @ CardPair(_, _) =>
      hand = h
      println(s"Received: $h")
    case Quit() =>
      println("Shutdown system")
      sender ! "Howdy"
      actorRefFactory.system.shutdown()
  }
}
