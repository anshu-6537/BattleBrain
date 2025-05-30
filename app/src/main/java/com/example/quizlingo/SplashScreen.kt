package com.example.quizlingo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val homeIntent= Intent(this@SplashScreen,ChoiceActivity::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, 3000)
    }
}