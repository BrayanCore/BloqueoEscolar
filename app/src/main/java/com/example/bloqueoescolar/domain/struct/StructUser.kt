package com.example.bloqueoescolar.domain.struct

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

class StructUser : Serializable {
    var id: String = ""
    var nombre: String = ""
    var ap_paterno: String = ""
    var ap_materno: String = ""
    var email: String = ""
    var password: String = ""

    constructor()

    constructor(
        id: String,
        nombre: String,
        ap_paterno: String,
        ap_materno: String,
        email: String,
        password: String
    )
}