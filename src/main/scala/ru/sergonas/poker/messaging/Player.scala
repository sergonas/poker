package ru.sergonas.poker.messaging

import akka.actor.{ActorLogging, Actor}
import ru.sergonas.poker.messaging.InputEvents._

class Player extends Actor with ActorLogging {
  var login: Option[String] = None
  var room: Option[String] = None

  override def receive: Receive = {
    case Login(username) =>
      login = Some(username)
      log.debug("Received login from {}", username)
    case JoinRoom(roomName) => login foreach { username =>
      room = Some(roomName)
      log.debug(s"User $username joined $roomName room")
    }
  }
}
