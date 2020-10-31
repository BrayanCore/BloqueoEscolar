package com.example.bloqueoescolar.domain.struct

import java.io.Serializable

data class StructExam(

    var name: String,
    var id: String,
    var questions: ArrayList<StructQuestion> = ArrayList()

) : Serializable {
    constructor()
}