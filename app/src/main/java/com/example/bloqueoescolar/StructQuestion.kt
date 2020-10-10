package com.example.bloqueoescolar

data class StructQuestion(
    var id: Int,
    var question: String,
    var correctAnswer: Int,
    var options: StructOptions
)