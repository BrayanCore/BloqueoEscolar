package com.example.bloqueoescolar

data class StructGrade(

    var id: String,
    var grade: Int,
    var subjects: ArrayList<StructExam> = ArrayList()

)