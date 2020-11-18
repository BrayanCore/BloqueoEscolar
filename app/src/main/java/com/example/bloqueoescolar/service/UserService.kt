package com.example.bloqueoescolar.service

import com.example.bloqueoescolar.domain.repository.UserRepository
import com.example.bloqueoescolar.domain.struct.StructUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.sql.Struct

class UserService {

    lateinit var repository: UserRepository

    fun registerUsuario(structUser: StructUser) {
        repository = UserRepository()
        repository.save(structUser)
    }
}