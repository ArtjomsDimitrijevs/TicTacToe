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

    private var isPvC: Boolean? = null
    //private lateinit var pvpButton: Button
    //private lateinit var pvcButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val pvpButton = findViewById<Button>(R.id.buttonPvP)
        val pvcButton = findViewById<Button>(R.id.buttonPvC)
        val playButton = findViewById<Button>(R.id.playButton)
        val player1NameInput = findViewById<EditText>(R.id.player1NameInput)
        val player2NameInput = findViewById<EditText>(R.id.player2NameInput)

        pvpButton.setOnClickListener {
            isPvC = false
            highlightSelectedButton(pvpButton)
            unhighlightButton(pvcButton)
            player1NameInput.visibility = View.VISIBLE
            player2NameInput.visibility = View.VISIBLE
        }
        pvcButton.setOnClickListener {
            isPvC = true
            highlightSelectedButton(pvcButton)
            unhighlightButton(pvpButton)
            player1NameInput.visibility = View.VISIBLE
            player2NameInput.visibility = View.GONE


        }
        playButton.setOnClickListener {
            if (isPvC == null) {
                Toast.makeText(this, "Please select a game mode.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var playerXName = player1NameInput.text.toString()
            val playerOName = if (player2NameInput.text.toString().isEmpty()) "Player O" else player2NameInput.text.toString()

            if (isPvC == true) {
                if (playerXName.isEmpty()) {
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
            putExtra("PLAYER_NAME_X", player1Name)
            putExtra("PLAYER_NAME_O", player2Name)
        }
        startActivity(intent)
    }

}
