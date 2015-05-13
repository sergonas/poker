package ru.sergonas.poker.messaging

import ru.sergonas.poker.messaging.InputEvents.InputEvent

object OutputEvents {
  trait OutputEvent extends GameEvent
  case class Broadcast(from: String, original: InputEvent) extends OutputEvent
}
