package com.example.bloqueoescolar.domain.struct

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class StructUser (
    var id: String,
    var nombre: String,
    var ap_paterno: String,
    var ap_materno: String,
    var email: String,
    var password: String
) : Serializable