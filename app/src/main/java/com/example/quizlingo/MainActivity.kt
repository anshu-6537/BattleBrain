package com.example.quizlingo

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import pl.droidsonroids.gif.GifTextView
import kotlin.coroutines.*


class MainActivity : AppCompatActivity() {


    var BASE_URL = "https://opentdb.com/api.php"
    var quesnum: Int = 0
    var cat: Int = 0
//    var curr_high: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val difficulty = intent.getStringExtra("diff")
        val number = intent.getStringExtra("num")
        val category = intent.getStringExtra("category")
//        val highscore = intent.getStringExtra("curr")
//        if (highscore != null) {
//            curr_high = highscore.toInt()
//        }
        quesnum = number?.toInt()!!
        if (category != null) {
            cat = category.toInt()
        }
        if (difficulty != null) {
            fetchQuestion(quesnum, difficulty, cat)
        } else {
            Toast.makeText(this, "difficulty and num can't be fetched", Toast.LENGTH_SHORT).show()
        }


    }

    fun fetchQuestion(
        amount: Int,
        difficulty: String,
        category: Int,
    ) {

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val go_gif = findViewById<GifTextView>(R.id.go_gif)
        val const = findViewById<ConstraintLayout>(R.id.constraint)

        val url = "$BASE_URL?amount=$amount&category=$category&difficulty=$difficulty&type=multiple"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val triviaQuestions = parseTriviaQuestions(response)
                //Toast.makeText(this, triviaQuestions.size.toString(), Toast.LENGTH_SHORT).show()
                val view: View = go_gif
                view.postDelayed(
                    {
                        view.visibility = View.GONE
                        const.visibility = View.VISIBLE
                    },
                    100
                )
                const.visibility = View.VISIBLE
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

        //Toast.makeText(this,"Yesss",Toast.LENGTH_SHORT).show()
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
                //Toast.makeText(this, triviaQuestions.size.toString(), Toast.LENGTH_SHORT).show()
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
        val next = findViewById<Button>(R.id.nextButton)
        val radioButton1 = findViewById<RadioButton>(R.id.answerRadioButton1)
        val radioButton2 = findViewById<RadioButton>(R.id.answerRadioButton2)
        val radioButton3 = findViewById<RadioButton>(R.id.answerRadioButton3)
        val radioButton4 = findViewById<RadioButton>(R.id.answerRadioButton4)
        val score = findViewById<TextView>(R.id.your_score)
        val your_score = findViewById<TextView>(R.id.score)
        val progress = findViewById<ProgressBar>(R.id.progressBar)
        val save = findViewById<Button>(R.id.saveButton)
        var i = 0
        var userscore = 0

        //Toast.makeText(this, questionArray[0].correctanswer, Toast.LENGTH_SHORT).show()

        questext.text = questionArray[i].question
        radioButton1.text = questionArray[i].incorrectanswer[1]
        radioButton2.text = questionArray[i].incorrectanswer[0]
        radioButton3.text = questionArray[i].correctanswer
        radioButton4.text = questionArray[i].incorrectanswer[2]
        val timer = findViewById<TextView>(R.id.textView)
        val text = findViewById<TextView>(R.id.percent)

        // time count down for 15 seconds,
        // with 1 second as countDown interval
//        object : CountDownTimer(10000, 1000) {
//
//            // Callback function, fired on regular interval
//            override fun onTick(millisUntilFinished: Long) {
//                timer.setText("Time remaining: " + millisUntilFinished / 1000)
//            }
//
//            // Callback function, fired
//            // when the time is up
//            override fun onFinish() {
//                timer.setText("Times up !")
//
//            }
//        }.start()
        val CountDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer.text = "Time remaining: " + millisUntilFinished / 1000
            }
            override fun onFinish() {
                timer.text = "Times up !"
            }
        }.start()



        val radioGroup = findViewById<RadioGroup>(R.id.radiogroup)
        next.setOnClickListener {

            val selectedId = radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedId)

            //Toast.makeText(this,radioButton.getText(),Toast.LENGTH_SHORT).show()
            //Toast.makeText(this,questionArray[i].correctanswer,Toast.LENGTH_SHORT).show()
            if (selectedId == -1)
                Toast.makeText(this, "You missed the last quiz", Toast.LENGTH_SHORT).show()
            else if (radioButton.text == questionArray[i].correctanswer) {
                userscore += 1
                //Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show()
            }
            radioGroup.clearCheck()
            timer.text = null
            CountDownTimer.cancel()
            i++
            if (i < questionArray.size) {
                questext.text = questionArray[i].question
                radioButton1.text = questionArray[i].incorrectanswer[1]
                radioButton2.text = questionArray[i].incorrectanswer[0]
                radioButton3.text = questionArray[i].correctanswer
                radioButton4.text = questionArray[i].incorrectanswer[2]
                CountDownTimer.start()
                //Toast.makeText(this,questionArray[i].correctanswer,Toast.LENGTH_SHORT).show()

                ////////////////////////////
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
                save.visibility = View.VISIBLE
                val percen =  (userscore.toFloat()/quesnum)  * 100
                progress.progress = percen.toInt()
                //progress.max=100
//                Toast.makeText(this,userscore.toString(),Toast.LENGTH_SHORT).show()
//                Toast.makeText(this,quesnum.toString(),Toast.LENGTH_SHORT).show()
                Toast.makeText(this,percen.toInt().toString(),Toast.LENGTH_SHORT).show()
                your_score.text = userscore.toString()
                val str=percen.toInt().toString()+"%"
                text.setText(str)
                val sh = getSharedPreferences("myPref", Context.MODE_PRIVATE)
                val editor = sh.edit()
                val highScore=sh.getInt("score",0)
                save.setOnClickListener {
                    if(userscore>highScore) {
                        editor.putInt("score",userscore)
                        editor.commit()
                        Log.d("helo",sh.getInt("score",0).toString())
                        Toast.makeText(this,sh.getInt("score",-1).toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}