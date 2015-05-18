package ru.sergonas.poker.messaging

import akka.actor.{ActorRef, ActorLogging, Actor}
import ru.sergonas.poker.materials.Cards.CardPair
import ru.sergonas.poker.messaging.InputEvents._
import ru.sergonas.poker.messaging.OutputEvents._

class Player extends Actor with ActorLogging {
  private[this] var login: Option[String] = None
  private[this] var room: Option[(String, ActorRef)] = None
  private[this] var hand: Option[CardPair] = None

  override def receive: Receive = {
    case Login(username) =>
      login = Some(username)
      log.debug("Received login from {}", username)
    case JoinRoom(roomName) => for (username <- login) {
      log.debug(s"Ask room service to join $roomName")
    }
    case RoomJoinApproved(roomRef, roomName) => room = Some(roomRef -> roomName)
    case deny: RoomJoinDenied => sender() ! deny
  }
}
