package com.example.bloqueoescolar

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bloqueoescolar.domain.repository.GradeRepository
import com.example.bloqueoescolar.domain.struct.StructExam
import com.example.bloqueoescolar.domain.struct.StructGrade
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.select_grade.*

class Grades : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance().getReference("Grados")
    var grades: ArrayList<StructGrade> = ArrayList()

    companion object {
        private const val TAG = "SelectGradeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_grade)
    }

    override fun onStart() {
        super.onStart()

        first_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 1)
            startActivity(intent)
        }

        second_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 2)
            startActivity(intent)
        }

        third_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 3)
            startActivity(intent)
        }

        fourth_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 4)
            startActivity(intent)
        }

        fifth_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 5)
            startActivity(intent)
        }

        sixth_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 6)
            startActivity(intent)
        }
    }

    fun getGrades() {
        grades = ArrayList()
        val gradesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var i = 0
                for (gradeSnapshot in dataSnapshot.children) { // Se guardan los grados

                    val grade: StructGrade = StructGrade()

                    grade.id = gradeSnapshot.child("id").getValue(String::class.java)!!
                    grade.grade = gradeSnapshot.child("grade").getValue(Int::class.java)!!

                    var j = 0
                    for(examSnapshot in gradeSnapshot.child("subjects").children) { // Se guardan examanes
                        val exam: StructExam = StructExam()
                        exam.id = examSnapshot.child("id").getValue(String::class.java)!!
                        exam.name = examSnapshot.child("name").getValue(String::class.java)!!

                        for(questionSnapshot in examSnapshot.child("questions").children) { // Se guardan las preguntas

                        }

                        j++
                    }

                    //Se agrega objeto grado a la lista de grados
                    grades[i] = grade

                    i++
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        database.addValueEventListener(gradesListener)
    }
}