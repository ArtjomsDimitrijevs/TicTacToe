package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {

    private var isPvC: Boolean? = null // nullable to enforce game mode selection before starting the game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // get all necessary elements from xml layout
        val pvpButton = findViewById<Button>(R.id.buttonPvP)
        val pvcButton = findViewById<Button>(R.id.buttonPvC)
        val playButton = findViewById<Button>(R.id.playButton)
        val player1NameInput = findViewById<EditText>(R.id.player1NameInput)
        val player2NameInput = findViewById<EditText>(R.id.player2NameInput)

        pvpButton.setOnClickListener {
            isPvC = false  // to control what game mode selected and intent data later
            // we know that selected mode is not PvC => is PvP
            highlightSelectedButton(pvpButton)
            unhighlightButton(pvcButton)
            // to display the input for names of both players for PvP mode
            player1NameInput.visibility = View.VISIBLE
            player2NameInput.visibility = View.VISIBLE
        }
        pvcButton.setOnClickListener {
            isPvC = true // we know that selected mode is PvC
            highlightSelectedButton(pvcButton)
            unhighlightButton(pvpButton)
            player1NameInput.visibility = View.VISIBLE
            player2NameInput.visibility = View.GONE // we need only 1 player name for PvC


        }
        playButton.setOnClickListener {
            if (isPvC == null) {
                Toast.makeText(this, "Please select a game mode.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // blocking the start of the game
                                          // by returning from the click listener
            }

            var playerXName = player1NameInput.text.toString()
                // if O player's name input is empty => "O" by default
                // but for X player checking will be in the PvC mode,
                // because in PvP mode you can have empty names (will be default X and O)
                // but for PvC name is necessary for starting the game
            val playerOName = if (player2NameInput.text.toString().isEmpty()) "Player O" else player2NameInput.text.toString()

            if (isPvC == true) {
                if (playerXName.isEmpty()) { // necessary name for player in PvC
                    Toast.makeText(this, "Please enter your name to play.", Toast.LENGTH_SHORT).show()
                } else {
                    startGame(playerXName)
                }
            }
            else {
                if (playerXName.isEmpty()) {
                    playerXName = "Player X"
                    startGame(playerXName, playerOName)
                }
                else
                    startGame(playerXName, playerOName)
            }



        }
    }

    private fun highlightSelectedButton(button: Button) {
        button.isSelected = true
    }

    private fun unhighlightButton(button: Button) {
        button.isSelected = false
    }


    private fun startGame(playerName: String) {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra("GAME_MODE", "PvC")
            putExtra("PLAYER_NAME", playerName)
        }
        startActivity(intent)
    }

    private fun startGame(player1Name: String, player2Name: String) {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra("GAME_MODE", "PvP")
            putExtra("PLAYER_X_NAME", player1Name)
            putExtra("PLAYER_O_NAME", player2Name)
        }
        startActivity(intent)
    }

}
