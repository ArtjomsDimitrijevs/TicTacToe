package com.example.tictactoe.game

class GameBoard {
    val board = Array(3) { Array(3) { Player.NONE} }

    fun getCell(row: Int, col: Int): Player = board[row][col]
    fun setCell(player: Player, row: Int, col: Int) : Boolean {
        if(board[row][col] == Player.NONE){
            board[row][col] = player
            return true
        }
        return false
    }

    fun isFull(): Boolean {
        return board.all { row -> row.all { it != Player.NONE }}
    }

    fun reset() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                board[i][j] = Player.NONE
            }
        }
    }

}
