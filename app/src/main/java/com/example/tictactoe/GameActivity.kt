package com.example.tictactoe

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.app.AlertDialog
import com.example.tictactoe.game.GameProgress
import com.example.tictactoe.game.GameProgressListener
import com.example.tictactoe.game.Player

class GameActivity : ComponentActivity(), GameProgressListener {

    private lateinit var turnTextView: TextView
    private lateinit var gameProgress: GameProgress
    private lateinit var gameMode: String
    private var isPvCMode: Boolean = false
    private var playerXName: String = ""
    private var playerOName: String = ""


    private val handler = Handler(Looper.getMainLooper()) // for delay before PvC makes turn


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        turnTextView = findViewById(R.id.turnTextView)


        gameMode = intent.getStringExtra("GAME_MODE") ?: "PvP"
        isPvCMode = (gameMode == "PvC")
        playerXName = intent.getStringExtra("PLAYER_NAME_X") ?: "Player X"
        playerOName = intent.getStringExtra("PLAYER_NAME_O") ?: "Player O"

        gameProgress = GameProgress(this)

        updateTurnText()
    }

    private fun updateTurnText() {
        val currentTurn = gameProgress.getCurrentTurn()
        val playerText = when (currentTurn) {
            Player.X -> "$playerXName's turn"
            Player.O -> if (isPvCMode) "Computer's turn" else "$playerOName's turn"
            else -> "Waiting for a turn"
        }
        turnTextView.text = playerText
    }

    override fun onTurnChanged(player: Player) {
        updateTurnText()
        if (isPvCMode && player == Player.O) {
            handler.postDelayed({
                makeComputerMove()
            }, 1000) // delay 1 second before PvC makes turn
        }
    }

    override fun onGameFinished(winner: Player) {
        val winnerText = when (winner) {
            Player.X -> "$playerXName wins!"
            Player.O -> "$playerOName wins!"
            else -> "It's a draw!"
        }

        AlertDialog.Builder(this)
            .setTitle("Game Over!")
            .setMessage(winnerText)
            .setPositiveButton("Restart game") { dialog, _ ->
                gameProgress = GameProgress(this)
                resetGame()
                updateTurnText()
                dialog.dismiss()
            }
            .setNeutralButton("Go back to main screen") { dialog, _ ->
                navigateToMainMenu()
                dialog.dismiss()
            }
            .show()
    }
    fun resetGame() {
        gameProgress = GameProgress(this)
        updateTurnText()

        val buttons = listOf(
            R.id.button00, R.id.button01, R.id.button02,
            R.id.button10, R.id.button11, R.id.button12,
            R.id.button20, R.id.button21, R.id.button22
        )

        for (buttonId in buttons) {
            val button = findViewById<Button>(buttonId)
            button.text = ""
            button.isEnabled = true
        }
    }


    private fun navigateToMainMenu() {
        finish()
    }


    fun onCellClicked(view: android.view.View) {
        val button = view as Button
        val id = button.id

        // map button's ids to row and column
        val (row, col) = when (id) {
            R.id.button00 -> 0 to 0
            R.id.button01 -> 0 to 1
            R.id.button02 -> 0 to 2
            R.id.button10 -> 1 to 0
            R.id.button11 -> 1 to 1
            R.id.button12 -> 1 to 2
            R.id.button20 -> 2 to 0
            R.id.button21 -> 2 to 1
            R.id.button22 -> 2 to 2
            else -> return
        }

        val currentPlayer = gameProgress.getCurrentTurn()

        val moveSuccessful = gameProgress.makeMove(row, col)
        if (moveSuccessful) {
            button.text = if (currentPlayer == Player.X) "X" else "O"
            button.isEnabled = false // disable button after move
        }
    }


    private fun makeComputerMove() {  // gemini in Android studio suggested this implementation of randoming computer's move
        val emptyCells = gameProgress.gameBoard.board.flatMapIndexed { rowIndex, row ->
            row.mapIndexedNotNull { colIndex, player ->
                if (player == Player.NONE) rowIndex * 3 + colIndex else null
            }
        }
        val randomIndex = (0 until emptyCells.size).random()
        val (row, col) = emptyCells[randomIndex] / 3 to emptyCells[randomIndex] % 3

        gameProgress.makeMove(row, col)

        // update the button after the computer's move
        val buttonId = resources.getIdentifier(
            "button$row$col", "id", packageName
        )
        val button = findViewById<Button>(buttonId)
        button.text = "O"  // assuming 'O' is the computer's symbol
        button.isEnabled = false // disable the button after computer's move
    }
}
