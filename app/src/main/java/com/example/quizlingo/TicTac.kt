package com.example.quizlingo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TicTac : AppCompatActivity() {
    var btn1: Button? = null
    var btn2: Button? = null
    var btn3: Button? = null
    var btn4: Button? = null
    var btn5: Button? = null
    var btn6: Button? = null
    var btn7: Button? = null
    var btn8: Button? = null
    var btn9: Button? = null
    var b1: String? = null
    var b2: String? = null
    var b3: String? = null
    var b4: String? = null
    var b5: String? = null
    var b6: String? = null
    var b7: String? = null
    var b8: String? = null
    var b9: String? = null
    var flag = 0
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac)
        init()
    }

    private fun init() {
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
    }

    fun Check(view: View) {
        val btnCurrent = view as Button
        if (btnCurrent.text.toString() == "") {
            count++
            if (flag == 0) {
                btnCurrent.text = "X"
                flag = 1
            } else {
                btnCurrent.text = "O"
                flag = 0
            }
            if (count > 4) {
                b1 = btn1!!.text.toString()
                b2 = btn2!!.text.toString()
                b3 = btn3!!.text.toString()
                b4 = btn4!!.text.toString()
                b5 = btn5!!.text.toString()
                b6 = btn6!!.text.toString()
                b7 = btn7!!.text.toString()
                b8 = btn8!!.text.toString()
                b9 = btn9!!.text.toString()

                //conditions
                if (b1 == b2 && b2 == b3 && b1 != "") {
                    //1
                    Toast.makeText(this, "Winner is :$b1", Toast.LENGTH_SHORT).show()
                    newGame()
                } else if (b4 == b5 && b5 == b6 && b4 != "") {
                    //2
                    Toast.makeText(this, "Winner is :$b4", Toast.LENGTH_SHORT).show()
                    newGame()
                } else if (b7 == b8 && b8 == b9 && b7 != "") {
                    //3
                    Toast.makeText(this, "Winner is :$b7", Toast.LENGTH_SHORT).show()
                    newGame()
                } else if (b1 == b4 && b4 == b7 && b1 != "") {
                    //4
                    Toast.makeText(this, "Winner is :$b1", Toast.LENGTH_SHORT).show()
                    newGame()
                } else if (b2 == b5 && b5 == b8 && b2 != "") {
                    //5
                    Toast.makeText(this, "Winner is :$b2", Toast.LENGTH_SHORT).show()
                    newGame()
                } else if (b3 == b6 && b6 == b9 && b3 != "") {
                    //6
                    Toast.makeText(this, "Winner is :$b3", Toast.LENGTH_SHORT).show()
                    newGame()
                } else if (b1 == b5 && b5 == b9 && b1 != "") {
                    //7
                    Toast.makeText(this, "Winner is :$b1", Toast.LENGTH_SHORT).show()
                    newGame()
                } else if (b3 == b5 && b5 == b7 && b3 != "") {
                    //8
                    Toast.makeText(this, "Winner is :$b3", Toast.LENGTH_SHORT).show()
                    newGame()
                } else if (count == 9) {
                    Toast.makeText(this, "Game is Drawn", Toast.LENGTH_SHORT).show()
                    newGame()
                }
            }
        }
    }

    fun newGame() {
        btn1!!.text = ""
        btn2!!.text = ""
        btn3!!.text = ""
        btn4!!.text = ""
        btn5!!.text = ""
        btn6!!.text = ""
        btn7!!.text = ""
        btn8!!.text = ""
        btn9!!.text = ""
        count = 0
        flag = 0
    }
}