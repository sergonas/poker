package ru.sergonas.poker.messaging

import akka.actor.{ActorRef, Actor}
import ru.sergonas.poker.materials.{Desk, Cards}
import ru.sergonas.poker.messaging.InputEvents._
import ru.sergonas.poker.messaging.OutputEvents._

class Room extends Actor {
  private[this] var players = Seq.empty[ActorRef]
  private[this] var currentPlayerMove: Option[ActorRef] = None
  private[this] var desk: Option[Desk] = None

  override def receive: Receive = {
    case JoinRoom(roomName) => if (players.length < 6) {
      players :+= sender()
    } else {
      sender() ! RoomJoinDenied(roomName, Some("Room is full"))
    }
    case Call =>
  }
}
