package com.example.bloqueoescolar.domain.struct

import java.io.Serializable

data class StructGrade(

    var id: String,
    var grade: Int,
    var subjects: ArrayList<StructExam> = ArrayList()

) : Serializable