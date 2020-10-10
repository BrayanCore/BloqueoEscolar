package com.example.bloqueoescolar

data class StructExam(

    var name: String,
    var id: Int,
    var questions: ArrayList<StructQuestion> = ArrayList()

)