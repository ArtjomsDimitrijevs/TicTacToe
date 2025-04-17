package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.tictactoe.game.GameProgress
import com.example.tictactoe.game.GameProgressListener
import com.example.tictactoe.game.Player


class GameActivity : ComponentActivity() {

    private lateinit var turnTextView: TextView
    private lateinit var gameProgress: GameProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        turnTextView = findViewById(R.id.turnTextView)
        //gameProgress = GameProgress(this)

    }

}