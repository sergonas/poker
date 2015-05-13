package ru.sergonas.poker.messaging

object InputEvents {
  trait InputEvent extends GameEvent

  case class Login(username: String) extends InputEvent
  case object Logout extends InputEvent
  case class JoinRoom(roomName: String) extends InputEvent
  case class Raise(bet: Int) extends InputEvent
  case object Check extends InputEvent
  case object Call extends InputEvent
  case object Fold extends InputEvent
  case object LeaveRoom extends InputEvent
  case class Message(text: String) extends InputEvent
}
