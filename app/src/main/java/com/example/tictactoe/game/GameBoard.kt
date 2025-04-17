package com.example.tictactoe.game

class GameBoard : IGameBoard {
    val board = Array(3) { Array(3) { Player.NONE} }

    override fun getCell(row: Int, col: Int): Player = board[row][col]     //get info about cell
    override fun setCell(player: Player, row: Int, col: Int) : Boolean {   //set info in cell if there isn't mark
        if(board[row][col] == Player.NONE){
            board[row][col] = player
            return true
        }
        return false
    }

    override fun isFull(): Boolean {
        return board.all { row -> row.all { it != Player.NONE }}
    }

    override fun reset() {                                                 //clear the board
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                board[i][j] = Player.NONE
            }
        }
    }

}
