package com.example.tictactoe.game

interface IGameBoard {
    fun getCell(row: Int, col: Int): Player
    fun setCell(player: Player, row: Int, col: Int): Boolean
    fun isFull(): Boolean
    fun reset()
}