package com.example.bloqueoescolar.domain.struct

import java.io.Serializable

class StructQuestion : Serializable {
    var question: String = ""
    var correctAnswer: Int = 0
    var options: StructOptions = StructOptions()

    constructor()

    constructor(
        question: String,
        correctAnswer: Int,
        options: StructOptions
    ) {
        this.question = question
        this.correctAnswer = correctAnswer
        this.options = options
    }
}