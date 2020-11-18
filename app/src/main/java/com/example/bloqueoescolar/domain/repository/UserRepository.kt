package com.example.bloqueoescolar.domain.repository

import com.example.bloqueoescolar.domain.struct.StructUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {

    fun save(structUser: StructUser) {
        val database = Firebase.database
        val myRef = database.getReference("usuario/${structUser.id}")
        // Guardar valores
        myRef.setValue(structUser)
    }
}