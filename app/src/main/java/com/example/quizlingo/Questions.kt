package com.example.quizlingo

data class Questions(

    val question : String,
    val correctanswer : String,
    val incorrectanswer : List<String>
)
