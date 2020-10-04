package com.example.bloqueoescolar.domain.entity

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Usuario (
    var id: String,
    var nombre: String,
    var ap_paterno: String,
    var ap_materno: String,
    var email: String,
    var password: String
)