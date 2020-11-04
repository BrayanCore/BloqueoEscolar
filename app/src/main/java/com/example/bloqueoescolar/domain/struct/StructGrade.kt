package com.example.bloqueoescolar.domain.struct

import java.io.Serializable
import javax.security.auth.Subject

class StructGrade : Serializable {

    var id: String = ""
    var grade: Int = 0
    var subjects: ArrayList<StructExam> = ArrayList()

    constructor()

    constructor(
        id: String,
        grade: Int,
        subjects: ArrayList<StructExam>
    )

}