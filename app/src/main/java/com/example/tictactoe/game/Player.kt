package com.example.tictactoe.game

enum class Player(symbol: Char) {
    X('X'),
    O('X'),
    NONE(' ');

    fun nextPlayer(): Player = if (this == X) O else X
}