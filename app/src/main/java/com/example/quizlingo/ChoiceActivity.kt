package com.example.quizlingo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class ChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)
        val rockGame = findViewById<ImageButton>(R.id.rock_paper_sci)
        val quizGame = findViewById<ImageButton>(R.id.quiz)
        val ticTacGame = findViewById<ImageButton>(R.id.tic_tac)

        rockGame.setOnClickListener {
            val intent = Intent(this, RockPaperScissor::class.java)
            startActivity(intent)
        }

        quizGame.setOnClickListener {
            val intent=Intent(this, SelectDetails::class.java)
            startActivity(intent)
        }

        ticTacGame.setOnClickListener {
            val intent = Intent(this, TicTac::class.java)
            startActivity(intent)
        }
    }
}