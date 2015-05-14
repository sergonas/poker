package ru.sergonas.poker.messaging

import akka.actor.Actor
import ru.sergonas.poker.messaging.InputEvents.JoinRoom

class RoomServiceActor extends Actor {

  override def receive: Receive = {
    case JoinRoom(roomName) =>
  }
}
