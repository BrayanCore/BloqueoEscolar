package com.example.bloqueoescolar.service

import com.example.bloqueoescolar.domain.repository.GradeRepository
import com.example.bloqueoescolar.domain.struct.StructGrade

class GradeService {
    var repository: GradeRepository = GradeRepository()

    fun getGrades() {
        return repository.getGrades()
    }
}