package com.example.bloqueoescolar.domain.struct

import java.io.Serializable

class StructNewUser : Serializable {
    var nombre: String = ""
    var email: String = ""
    var password: String = ""
    var confirmPassword: String = ""

    constructor()

    constructor(
        nombre: String,
        email: String,
        password: String,
        confirmPassword: String
    )
}