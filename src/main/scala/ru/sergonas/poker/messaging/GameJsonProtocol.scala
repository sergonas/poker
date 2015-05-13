package ru.sergonas.poker.messaging

import spray.json.DefaultJsonProtocol

object GameJsonProtocol extends DefaultJsonProtocol {
  import InputEvents._

  implicit val loginFormat = jsonFormat1(Login)
}
