package ru.sergonas.poker.materials

import ru.sergonas.poker.materials.Cards.Card

class Player {
  var _hand : (Card, Card)
  def hand = _hand
  def hand_= (l: Card, r: Card) = _hand = (l, r)
}
