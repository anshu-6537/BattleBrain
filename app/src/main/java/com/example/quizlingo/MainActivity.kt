package com.example.quizlingo

import android.R.attr.button
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {



    var BASE_URL = "https://opentdb.com/api.php"
    var quesnum: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val difficulty = intent.getStringExtra("diff")
        val number = intent.getStringExtra("num")
        Toast.makeText(this,difficulty,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,number,Toast.LENGTH_SHORT).show()
        quesnum = number?.toInt()!!
        if (difficulty != null) {
            fetchQuestion(quesnum, difficulty)
        } else {
            Toast.makeText(this, "difficulty and num can't be fetched", Toast.LENGTH_SHORT).show()
        }


    }

    fun fetchQuestion(
        amount: Int,
        difficulty: String
    ) {

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)


        val url = "$BASE_URL?amount=$amount&category=28&difficulty=$difficulty&type=multiple"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val triviaQuestions = parseTriviaQuestions(response)
                Toast.makeText(this, triviaQuestions[0].question, Toast.LENGTH_LONG).show()
                displayQues(triviaQuestions)
            },
            { error ->
                Toast.makeText(this, "Unknown error occurred", Toast.LENGTH_SHORT).show()
            }

        )
        requestQueue.add(jsonObjectRequest)
    }

    private fun parseTriviaQuestions(response: JSONObject): List<Questions> {
        val resultsArray = response.optJSONArray("results")

        val triviaQuestions = mutableListOf<Questions>()
        resultsArray?.let {
            for (i in 0 until it.length()) {

                val questionObject = it.optJSONObject(i)
                val question = questionObject.optString("question")
                val correctAnswer = questionObject.optString("correct_answer")
                val incorrectAnswersArray = questionObject.optJSONArray("incorrect_answers")
                val incorrectAnswers = parseIncorrectAnswers(incorrectAnswersArray)
                val triviaQuestion = Questions(question, correctAnswer, incorrectAnswers)
                triviaQuestions.add(triviaQuestion)
            }
        }
        return triviaQuestions
    }

    private fun parseIncorrectAnswers(incorrectAnswersArray: JSONArray?): List<String> {
        val incorrectAnswers = mutableListOf<String>()
        incorrectAnswersArray?.let {
            for (i in 0 until it.length()) {
                val incorrectAnswer = it.optString(i)
                incorrectAnswers.add(incorrectAnswer)
            }
        }
        return incorrectAnswers
    }

    private fun displayQues(questionArray: List<Questions>) {
        val questext = findViewById<TextView>(R.id.questionTextView)
        val ans1 = findViewById<TextView>(R.id.option1)
        val ans2 = findViewById<TextView>(R.id.option2)
        val ans3 = findViewById<TextView>(R.id.option3)
        val ans4 = findViewById<TextView>(R.id.option4)
        val next = findViewById<Button>(R.id.nextButton)
        val radioButton1 = findViewById<RadioButton>(R.id.answerRadioButton1)
        val radioButton2 = findViewById<RadioButton>(R.id.answerRadioButton2)
        val radioButton3 = findViewById<RadioButton>(R.id.answerRadioButton3)
        val radioButton4 = findViewById<RadioButton>(R.id.answerRadioButton4)
        val score = findViewById<TextView>(R.id.your_score)
        val your_score = findViewById<TextView>(R.id.score)
        var i=0

        Toast.makeText(this, questionArray[0].correctanswer, Toast.LENGTH_SHORT).show()

        questext.text = questionArray[i].question
        ans1.text = questionArray[i].incorrectanswer[1]
        ans2.text = questionArray[i].incorrectanswer[0]
        ans3.text = questionArray[i].correctanswer
        ans4.text = questionArray[i].incorrectanswer[2]

        next.setOnClickListener {
            i++
            if (i<questionArray.size) {
                questext.text = questionArray[i].question
                ans1.text = questionArray[i].incorrectanswer[1]
                ans2.text = questionArray[i].incorrectanswer[0]
                ans3.text = questionArray[i].correctanswer
                ans4.text = questionArray[i].incorrectanswer[2]
            }
            else
            {
                radioButton1.visibility = View.INVISIBLE
                radioButton2.visibility = View.INVISIBLE
                radioButton3.visibility = View.INVISIBLE
                radioButton4.visibility = View.INVISIBLE
                questext.visibility = View.INVISIBLE
                ans1.visibility = View.INVISIBLE
                ans2.visibility = View.INVISIBLE
                ans3.visibility = View.INVISIBLE
                ans4.visibility = View.INVISIBLE
                next.visibility = View.INVISIBLE
                score.visibility = View.VISIBLE
                your_score.visibility = View.VISIBLE
            }
        }
    }
}

//        var i=0
//        while (i<questionArray.size) {
//            val ques = questionArray[i].question
//            val correct = questionArray[i].correctanswer
//            val incorrect1 = questionArray[i].incorrectanswer[0]
//            val incorrect2 = questionArray[i].incorrectanswer[1]
//            val incorrect3 = questionArray[i].incorrectanswer[2]
//            questext.text = ques
//            ans1.text = incorrect2
//            ans2.text = incorrect3
//            ans3.text = correct
//            ans4.text = incorrect1
//
//            //check next is pressed or not
//            var clicked = false
//
//            //my button clic
//
//            //my button clic
//            next.setOnClickListener { //change boolean value
//                clicked = true
//                if (clicked) {
//                    i += 1
//                }
//            }
//        }
//        for (i in questionArray.indices)
//        {
//            val ques = questionArray[i].question
//            val correct = questionArray[i].correctanswer
//            val incorrect1 = questionArray[i].incorrectanswer[0]
//            val incorrect2 = questionArray[i].incorrectanswer[1]
//            val incorrect3 = questionArray[i].incorrectanswer[2]
//
//            questext.text = ques
//            ans1.text = incorrect1
//            ans2.text = incorrect2
//            ans3.text = correct
//            ans4.text = incorrect3
//
//        }

//        questext.text = "Quiz Completed"
//        ans1.isEnabled = false
//        ans2.isEnabled = false
//        ans3.isEnabled = false
//        ans4.isEnabled = false
//        next.visibility = View.INVISIBLE

