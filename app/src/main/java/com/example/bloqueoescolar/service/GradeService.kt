package com.example.bloqueoescolar.service

import com.example.bloqueoescolar.domain.repository.GradeRepository

class GradeService {
    var repository: GradeRepository = GradeRepository()

    fun getGrades() {
        repository.getGrades()
    }
}