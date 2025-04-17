package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {

    private var isPvC: Boolean = false
    private lateinit var pvpButton: Button
    private lateinit var pvcButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val pvpButton = findViewById<Button>(R.id.buttonPvP)
        val pvcButton = findViewById<Button>(R.id.buttonPvC)
        val playButton = findViewById<Button>(R.id.playButton)
        val playerNameInput = findViewById<EditText>(R.id.playerNameInput)


        pvpButton.setOnClickListener {
            isPvC = false
            highlightSelectedButton(pvpButton)
            unhighlightButton(pvcButton)
        }
        pvcButton.setOnClickListener {
            isPvC = true
            highlightSelectedButton(pvcButton)
            unhighlightButton(pvpButton)

            playButton.setOnClickListener {
                val playerName = playerNameInput.text.toString()
                if (playerName.isEmpty()) {
                    Toast.makeText(this, "Please enter your name to play.", Toast.LENGTH_SHORT).show()
                } else {
                    startGame(playerName)
                }
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
            putExtra("GAME_MODE", if (isPvC) "PvC" else "PvP")
            putExtra("PLAYER_NAME", playerName)
        }
        startActivity(intent)
    }

}
