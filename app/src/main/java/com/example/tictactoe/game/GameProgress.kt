package com.example.tictactoe.game
import kotlin.random.Random

class GameProgress {
    val gameBoard = GameBoard()
    var currentPlayer = Player.X
        private set

    fun getBoards(): GameBoard = gameBoard

    //TO-DO fun for making move, all logic, all checks, etc.
    //fun makeMove(): Boolean
}