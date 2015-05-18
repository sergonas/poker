package ru.sergonas.poker.messaging

import akka.actor.ActorRef
import ru.sergonas.poker.messaging.InputEvents.InputEvent

object OutputEvents {
  trait OutputEvent extends GameEvent
  case class Broadcast(from: String, original: InputEvent) extends OutputEvent
  case class RoomJoinDenied(roomName: String, reason: Option[String]) extends OutputEvent
  case class RoomJoinApproved(roomName: String, room: ActorRef) extends OutputEvent
}
