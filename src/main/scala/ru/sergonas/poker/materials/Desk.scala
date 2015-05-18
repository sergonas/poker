package ru.sergonas.poker.materials

import scala.math.BigInt

class Desk(val cardsOnDesk: List[Cards.Card], val betByPlayer: Map[String, BigInt]) {
  def addCard(card: Cards.Card): Option[Desk] = Desk(card :: cardsOnDesk, betByPlayer)
  def addBet(name: String, bigInt: BigInt) = Desk(cardsOnDesk, betByPlayer + (name -> (betByPlayer(name) + bigInt)))
}

object Desk {
  def apply(cardsOnDesk: List[Cards.Card], betByPlayer: Map[String, BigInt]): Option[Desk] = {
    if (cardsOnDesk.length >= 3 && cardsOnDesk.length <= 5)
      Some(new Desk(cardsOnDesk, betByPlayer))
    else
      None
  }
}
