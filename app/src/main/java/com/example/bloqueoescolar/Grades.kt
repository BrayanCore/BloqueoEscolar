package com.example.bloqueoescolar

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.example.bloqueoescolar.domain.repository.GradeRepository
import com.example.bloqueoescolar.domain.struct.StructExam
import com.example.bloqueoescolar.domain.struct.StructGrade
import com.example.bloqueoescolar.domain.struct.StructOptions
import com.example.bloqueoescolar.domain.struct.StructQuestion
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.select_grade.*

class Grades : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance().getReference("Grados")
    var grades: ArrayList<StructGrade> = ArrayList()
    private lateinit var contenedor: LinearLayout

    companion object {
        private const val TAG = "SelectGradeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_grade)
        contenedor = findViewById(R.id.gradesContainer)
        getGrades()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun showGrades() {
        for(i in 0 until grades.size) {

            when(i) {
                0 -> {
                    first_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i))
                        startActivity(intent)
                    }
                }

                1 -> {
                    second_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i))
                        startActivity(intent)
                    }
                }

                2 -> {
                    third_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i))
                        startActivity(intent)
                    }
                }

                3 -> {
                    fourth_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i))
                        startActivity(intent)
                    }
                }

                4-> {
                    fifth_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i))
                        startActivity(intent)
                    }
                }

                5 -> {
                    sixth_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i))
                        startActivity(intent)
                    }
                }

                else -> {

                }
            }
        }
    }

    private fun getGrades() {
        grades = ArrayList()
        val gradesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (gradeSnapshot in dataSnapshot.children) { // Se guardan los grados
                    val grade = StructGrade()
                    grade.id = gradeSnapshot.child("id").getValue(String::class.java)!!
                    grade.grade = gradeSnapshot.child("grade").getValue(Int::class.java)!!

                    for (examSnapshot in gradeSnapshot.child("subjects").children) { // Se guardan examanes
                        val exam = StructExam()
                        exam.id = examSnapshot.child("id").getValue(String::class.java)!!
                        exam.name = examSnapshot.child("name").getValue(String::class.java)!!

                        for (questionSnapshot in examSnapshot.child("questions").children) { // Se guardan las preguntas
                            val question = StructQuestion()
                            question.question = questionSnapshot.child("question").getValue(String::class.java)!!
                            question.correctAnswer = questionSnapshot.child("correctAnswer").getValue(Int::class.java)!!

                            // Options
                            val options = questionSnapshot.child("options").getValue(StructOptions::class.java)!!
                            question.options = options

                            exam.questions.add(question) // Se agregan preguntas al examen
                        }
                        grade.subjects.add(exam) // Se agregan examenes al grado o grupo
                    }
                    grades.add(grade) //Se agrega objeto grado a la lista de grados
                }

                // Fun para imprimir los botones
                showGrades()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        database.addValueEventListener(gradesListener)
    }
}