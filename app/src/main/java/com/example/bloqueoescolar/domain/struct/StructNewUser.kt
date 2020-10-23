package com.example.bloqueoescolar.domain.struct

import java.io.Serializable

data class StructNewUser (
    var nombre: String,
    var email: String,
    var password: String,
    var confirmPassword: String
) : Serializable