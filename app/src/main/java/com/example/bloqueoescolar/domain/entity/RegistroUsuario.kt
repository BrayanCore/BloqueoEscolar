package com.example.bloqueoescolar.domain.entity

data class RegistroUsuario (
    var nombre: String,
    var email: String,
    var password: String,
    var confirmPassword: String
)