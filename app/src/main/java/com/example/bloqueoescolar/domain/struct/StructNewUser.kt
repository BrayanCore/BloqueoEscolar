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
    ) {
        this.nombre = nombre
        this.email = email
        this.password = password
        this.confirmPassword = confirmPassword
    }
}