package com.example.bloqueoescolar

data class User(

    var id: String,
    var name: String,
    var email: String,
    var password: String,
    var approved: ArrayList<String>
)