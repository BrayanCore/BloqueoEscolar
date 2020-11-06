package com.example.bloqueoescolar.domain.struct

import java.io.Serializable

class StructExam : Serializable {

    var name: String = ""
    var id: String = ""
    var questions: ArrayList<StructQuestion> = ArrayList()

    constructor()

    constructor (
        name: String,
        id: String,
        questions: ArrayList<StructQuestion>
    ) {
        this.name = name
        this.id = id
        this.questions = questions
    }
}