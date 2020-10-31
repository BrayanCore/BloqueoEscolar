package com.example.bloqueoescolar.domain.repository

import android.content.ContentValues
import android.util.Log
import com.example.bloqueoescolar.domain.struct.StructGrade
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class GradeRepository {

    var database: FirebaseDatabase = Firebase.database
    var reference: DatabaseReference = database.reference
    lateinit var listGrades: List<StructGrade>

    constructor() {

    }

    fun getGrades() {
        listGrades = ArrayList()

        val gradesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (gradeSnapshot in dataSnapshot.children) {
                    val grade: StructGrade = StructGrade()

                    grade.id = gradeSnapshot.child("id").getValue(String::class.java)!!
                    grade.grade = gradeSnapshot.child("grade").getValue(Int::class.java)!!
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        reference.addValueEventListener(gradesListener)
    }
}