package ru.sergonas.poker.materials

object Cards {
  object Suit extends Enumeration {
    type Suit = Value
    val Spades = Value("\u2660")
    val Hearts = Value("\u2665")
    val Diamonds = Value("\u2666")
    val Clubs = Value("\u2663")
  }

  import Suit._

  object Rank extends Enumeration {
    type Rank = Value
    val Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace = Value
  }

  import Rank._

  case class Card(suit: Suit, rank: Rank)
  case class CardPair(l: Card, r: Card)
}
