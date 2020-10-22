package com.example.bloqueoescolar.service

import com.example.bloqueoescolar.domain.struct.StructUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UsuarioService {

    fun registerUsuario(idUsuario: String, structUser: StructUser) {
        val database = Firebase.database
        val myRef = database.getReference("usuario/$idUsuario")

        // Guardar valores
        myRef.setValue(structUser)
    }
}