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
    ) {
        this.id = id
        this.nombre = nombre
        this.ap_paterno = ap_paterno
        this.ap_materno = ap_materno
        this.email = email
        this.password = password
    }
}