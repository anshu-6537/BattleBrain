package com.example.quizlingo

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import pl.droidsonroids.gif.GifTextView


class MainActivity : AppCompatActivity() {


    var BASE_URL = "https://opentdb.com/api.php"
    var quesnum: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val difficulty = intent.getStringExtra("diff")
        val number = intent.getStringExtra("num")
        //Toast.makeText(this,difficulty,Toast.LENGTH_SHORT).show()
        //Toast.makeText(this,number,Toast.LENGTH_SHORT).show()
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
        val go_gif = findViewById<GifTextView>(R.id.go_gif)
        val const = findViewById<ConstraintLayout>(R.id.constraint)


        val url = "$BASE_URL?amount=$amount&category=28&difficulty=$difficulty&type=multiple"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val triviaQuestions = parseTriviaQuestions(response)
                val view: View = go_gif
                view.postDelayed(
                    {
                        view.visibility = View.GONE
                        const.visibility = View.VISIBLE
                    },
                    3000
                )
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
//        val ans1 = findViewById<TextView>(R.id.option1)
//        val ans2 = findViewById<TextView>(R.id.option2)
//        val ans3 = findViewById<TextView>(R.id.option3)
//        val ans4 = findViewById<TextView>(R.id.option4)
        val next = findViewById<Button>(R.id.nextButton)
        val radioButton1 = findViewById<RadioButton>(R.id.answerRadioButton1)
        val radioButton2 = findViewById<RadioButton>(R.id.answerRadioButton2)
        val radioButton3 = findViewById<RadioButton>(R.id.answerRadioButton3)
        val radioButton4 = findViewById<RadioButton>(R.id.answerRadioButton4)
        val score = findViewById<TextView>(R.id.your_score)
        val your_score = findViewById<TextView>(R.id.score)
        val progress = findViewById<ProgressBar>(R.id.progressBar)
        var i = 0
        var userscore = 0

        //Toast.makeText(this, questionArray[0].correctanswer, Toast.LENGTH_SHORT).show()

        questext.text = questionArray[i].question
        radioButton1.text = questionArray[i].incorrectanswer[1]
        radioButton2.text = questionArray[i].incorrectanswer[0]
        radioButton3.text = questionArray[i].correctanswer
        radioButton4.text = questionArray[i].incorrectanswer[2]
        val timer = findViewById<TextView>(R.id.textView)

        // time count down for 15 seconds,
        // with 1 second as countDown interval
        object : CountDownTimer(14000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                timer.setText("Time remaining: " + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                timer.setText("Times up !")

            }
        }.start()

        val radioGroup = findViewById<RadioGroup>(R.id.radiogroup);

        next.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedId)
            if (radioButton.getText() == questionArray[i].correctanswer)
                userscore += 1
            radioGroup.clearCheck()
        }

        next.setOnClickListener {
            i++
            if (i < questionArray.size) {
                questext.text = questionArray[i].question
                radioButton1.text = questionArray[i].incorrectanswer[1]
                radioButton2.text = questionArray[i].incorrectanswer[0]
                radioButton3.text = questionArray[i].correctanswer
                radioButton4.text = questionArray[i].incorrectanswer[2]

                // get selected radio button from radioGroup
                val selectedId = radioGroup.checkedRadioButtonId

                // find the radiobutton by returned id
                val radioButton = findViewById<RadioButton>(selectedId)
                if (radioButton.getText() == questionArray[i].correctanswer)
                    userscore += 1
                radioGroup.clearCheck()
            } else {
                radioButton1.visibility = View.INVISIBLE
                radioButton2.visibility = View.INVISIBLE
                radioButton3.visibility = View.INVISIBLE
                radioButton4.visibility = View.INVISIBLE
                questext.visibility = View.INVISIBLE

                next.visibility = View.INVISIBLE
                score.visibility = View.VISIBLE
                your_score.visibility = View.VISIBLE
                progress.visibility = View.VISIBLE
                your_score.text = userscore.toString()

            }
        }
    }
}



