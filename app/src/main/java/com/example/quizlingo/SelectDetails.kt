package com.example.quizlingo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.w3c.dom.Text


class SelectDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_details)
        val curr_high=0;

        val score=findViewById<TextView>(R.id.score)
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        if(sharedPref.getInt("score",0)==0) {
            val editor = sharedPref.edit()
            editor.apply {
                putInt("score", curr_high)
                apply()
            }
        }
        else {
            val currScore = sharedPref.getInt("score", 0).toString()
            score.text = currScore
        }

        val category = arrayOf("General Knowledge", "Books","Music","Film","Video Games", "Mythology", "Sports", "Anime &amp; Manga", "Vehicles")
        val categoryImg = intArrayOf(R.drawable.knowledge,R.drawable.books,R.drawable.music, R.drawable.films,R.drawable.video_games,R.drawable.mythology,R.drawable.sports,R.drawable.anime,R.drawable.vehicle)

        val spinnerCategory = findViewById<Spinner>(R.id.categorySpinner)
        val customAdapter = CustomAdapter(applicationContext, categoryImg, category)
        spinnerCategory.adapter = customAdapter

        val spinnerDifficulty = findViewById<Spinner>(R.id.difficultySpinner)
        val spinnerQuestions = findViewById<Spinner>(R.id.numQuestionsSpinner)

        val level = arrayOf("Easy", "Medium", "Hard")
        val questions = arrayOf("5", "10", "15", "20")
        var difficulty = ""
        var numQuestions = ""
        var cat = ""
//        var catImg =""

        val arrayAdaplvl = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, level)
        val arrayAdapQues =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, questions)
//        val arrayAdapCategory =
//            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category)
//        val arrayAdapImg =
//            ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, categoryImg)

        spinnerDifficulty.adapter = arrayAdaplvl
        spinnerQuestions.adapter = arrayAdapQues
//        spinnerCategory.adapter = arrayAdapCategory
//        spinnerCategory.adapter = arrayAdapImg

        spinnerDifficulty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                difficulty = level[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerQuestions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                numQuestions = questions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                cat=category[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

//        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                cat = category[position]
//                Toast.makeText(this@SelectDetails,cat,Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }
        var catInt = -1

        // Set up the submit button click listener

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            if(cat == "General Knowledge" )
                catInt=9
            else if(cat == "Books")
                catInt=10
            else if(cat == "Music")
                catInt=12
            else if(cat == "Film")
                catInt=11
            else if(cat == "Video Games")
                catInt=15
            else if(cat == "Mythology")
                catInt=20
            else if(cat == "Sports")
                catInt=21
            else if(cat == "Anime &amp; Manga")
                catInt=31
            else if(cat == "Vehicles")
                catInt=28

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("diff", difficulty.toLowerCase())
            intent.putExtra("num", numQuestions)
            intent.putExtra("category", catInt.toString())
//            intent.putExtra("curr" , curr_high)
            //Toast.makeText(this,catInt.toString(),Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}




