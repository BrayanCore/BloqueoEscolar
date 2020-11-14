package com.example.bloqueoescolar

data class User(

    var id: String,
    var name: String,
    var email: String,
    var password: String,
    var approved: ArrayList<String>

    /*var id: String,
    var nombre: String,
    var ap_paterno: String,
    var ap_materno: String,
    var email: String,
    var password: String*/

)