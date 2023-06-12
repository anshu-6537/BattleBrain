package com.example.quizlingo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.*


class SelectDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_details)

        val spinnerDifficulty = findViewById<Spinner>(R.id.difficultySpinner)
        val spinnerQuestions = findViewById<Spinner>(R.id.numQuestionsSpinner)
        val level = arrayOf("Easy","Medium","Hard")
        val questions = arrayOf("5","10","15","20")

        var difficulty=""
        var numQuestions=""

        val arrayAdaplvl = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,level)
        val arrayAdapQues = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,questions)

        spinnerDifficulty.adapter = arrayAdaplvl

        spinnerDifficulty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                difficulty = level[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerQuestions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                numQuestions = questions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // Set up the submit button click listener
        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {

                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("diff",difficulty.toLowerCase())
                intent.putExtra("num",numQuestions)
                startActivity(intent)
            }
        }
    }




