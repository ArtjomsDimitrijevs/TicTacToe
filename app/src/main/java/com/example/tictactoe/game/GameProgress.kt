package com.example.tictactoe.game

import kotlin.random.Random

interface GameProgressListener {
    fun onTurnChanged(player: Player)
    fun onGameFinished(winner: Player)
}


class GameProgress(private val listener: GameProgressListener) : IGameBoard by GameBoard() {
    val gameBoard = GameBoard()
    val firstTurn = Player.X
    var currentTurn = Player.X
        private set
    fun getCurrentTurn(): Player = currentTurn


    fun makeMove(row: Int, col: Int): Boolean {
        if (!gameBoard.setCell(currentTurn, row, col)) return false

        val winner = checkWinner()

        if (winner != Player.NONE) {
            listener.onGameFinished(winner)  // notify about end of the game
        } else if (!gameBoard.isFull()) {
            currentTurn = if (currentTurn == Player.X) Player.O else Player.X
            listener.onTurnChanged(currentTurn)  // notify about turn change
        }

        return true
    }

    fun checkWinner(): Player {
        val b = Array(3) { row -> Array(3) { col -> gameBoard.getCell(row, col) } }

        // check horizontal lines
        for (i in 0..2) {
            if (b[i][0] != Player.NONE && b[i][0] == b[i][1] && b[i][1] == b[i][2]) return b[i][0]
            if (b[0][i] != Player.NONE && b[0][i] == b[1][i] && b[1][i] == b[2][i]) return b[0][i]
        }

        // check diagonal lines
        if (b[0][0] != Player.NONE && b[0][0] == b[1][1] && b[1][1] == b[2][2]) return b[0][0]
        if (b[0][2] != Player.NONE && b[0][2] == b[1][1] && b[1][1] == b[2][0]) return b[0][2]

        return Player.NONE
    }

}