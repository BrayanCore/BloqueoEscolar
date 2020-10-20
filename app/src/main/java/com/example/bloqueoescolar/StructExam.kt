package com.example.bloqueoescolar

data class StructExam(

    var name: String,
    var id: String,
    var questions: ArrayList<StructQuestion> = ArrayList()

)