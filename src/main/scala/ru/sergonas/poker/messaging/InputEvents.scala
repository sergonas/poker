package ru.sergonas.poker.messaging

import spray.json._
import spray.json.JsonParser.ParsingException
import ru.sergonas.poker.messaging.GameJsonProtocol._

object InputEvents {
  def innerToInputEvent(inner: InnerEventRepr): InputEvent = {
    inner match {
      case InnerEventRepr("login", params) => Login(params("username"))
      case InnerEventRepr("logout", _) => Logout
      case InnerEventRepr("joinRoom", params) => JoinRoom(params("roomName"))
      case InnerEventRepr("raise", params) => Raise(params("bet").toInt)
      case InnerEventRepr("check", _) => Check
      case InnerEventRepr("call", _) => Call
      case InnerEventRepr("fold", _) => Fold
      case InnerEventRepr("leaveRoom", _) => LeaveRoom
      case InnerEventRepr("message", params) => Message(params("text"))
    }
  }

  def inputToInnerEvent(input: InputEvent): InnerEventRepr = {
    input match {
      case Login(un) => InnerEventRepr("login", Map("username" -> un))
      case Logout => InnerEventRepr("logout", Map())
      case JoinRoom(roomName) => InnerEventRepr("joinRoom", Map("roomName" -> roomName))
      case Raise(bet) => InnerEventRepr("raise", Map("bet" -> bet.toString))
      case Check => InnerEventRepr("check", Map())
      case Call => InnerEventRepr("call", Map())
      case Fold => InnerEventRepr("fold", Map())
      case LeaveRoom => InnerEventRepr("leaveRoom", Map())
      case Message(text) => InnerEventRepr("message", Map("text" -> text))
    }
  }

  def tryParseEvent(input: String): Either[InputEvent, String] = {
    val json = try input.parseJson catch {
      case ex: ParsingException => return Right("ERROR: Not a valid JSON")
    }
    try Left(innerToInputEvent(json.convertTo[InnerEventRepr])) catch {
      case ex: RuntimeException => Right("ERROR: Unacceptable JSON structure")
    }
  }

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

  case class InnerEventRepr(eventName: String, params: Map[String, String])
}