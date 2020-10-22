package com.example.bloqueoescolar.domain.struct

import java.io.Serializable

data class StructQuestion(
    var question: String,
    var correctAnswer: Int,
    var options: StructOptions
) : Serializable