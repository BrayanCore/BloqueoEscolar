package com.example.bloqueoescolar

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bloqueoescolar.domain.struct.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_score.*
import kotlinx.android.synthetic.main.exams_options.*
import kotlin.collections.ArrayList
import java.util.Arrays

class Exams : AppCompatActivity(){

    val approvedSubjects = arrayOf("Biología", "Matematicas", "Química", "Español", "Ética", "Cursiva", "Ed.Física", "Historia")
    val pendingSubjects = arrayOf("Biología", "Matematicas", "Química", "Español", "Ética", "Cursiva", "Ed.Física", "Historia")
    var gradeTest = StructGrade("",0, ArrayList<StructExam>())
    val database = FirebaseDatabase.getInstance().getReference("Grados")
    var indexGrade = 0
    var examsApproved = ArrayList<String>()

    private lateinit var contenedor1: LinearLayout
    private lateinit var contenedor2: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exams_options)

        contenedor1 = findViewById(R.id.AvailableExams)
        contenedor2 = findViewById(R.id.Layout)

        indexGrade = intent.getIntExtra("Grade",100)
        examsApproved = intent.getStringArrayListExtra("examsApproved")!!
        loadInformation(indexGrade)

        // Crear examen activity
        create_exam.setOnClickListener {
            val intent = Intent(applicationContext, AddQuestion::class.java)
            startActivity(intent)
        }

    }

    /**
     * Funcion para imprimir todos los examenes
     */
    fun printAllExams(contenedor: LinearLayout, contenedor2: LinearLayout) {
        for (exam in gradeTest.subjects!!) {

            //val found = Arrays.stream(examsApproved).anyMatch { t -> t == exam.id }
            val found:Boolean = examsApproved.contains(exam.id)

            val boton = Button(applicationContext)
            boton.text = exam.name

            if (found) {
                boton.setBackgroundResource(R.drawable.boton_enviar)
                boton.setOnClickListener {
                    Toast.makeText(applicationContext,"Exámen Aprobado", Toast.LENGTH_LONG).show()
                }
                contenedor2.addView(boton)
            }
            else {
                boton.setBackgroundResource(R.drawable.boton_respuestas)
                boton.setOnClickListener(){
                    val intent = Intent(applicationContext, Question::class.java)
                    intent.putExtra("questions", exam.questions)
                    intent.putExtra("id", exam.id)
                    intent.putExtra("indexGrade", indexGrade)
                    startActivity(intent)
                    finish()
                }
                contenedor.addView(boton)
            }

        }
    }

    @Deprecated("Funcion no utilizada")
    fun ApprovedExams(elementos: Int, contenedor: LinearLayout) {
        for (i in 0 until elementos) {
            //creando un objeto de la clase button
            val boton = Button(applicationContext)
            //Personalizando botones
            boton.text = approvedSubjects.get(i)
            boton.setBackgroundResource(R.drawable.boton_respuestas)
            boton.setOnClickListener(){
                val intent = Intent(applicationContext, Question::class.java)
                intent.putExtra("Index",approvedSubjects.get(i))
                startActivity(intent)

            }
            contenedor.addView(boton)
        }
    }

    @Deprecated("Funcion no utilizada")
    fun PendingExams(elementos: Int, contenedor: LinearLayout) {
        for (i in 0 until elementos) {
            //creando un objeto de la clase button
            val boton = Button(applicationContext)
            //Personalizando botones
            boton.text = pendingSubjects.get(i)
            boton.setBackgroundResource(R.drawable.boton_enviar)
            boton.setOnClickListener(){
                val intent = Intent(applicationContext, Question::class.java)
                intent.putExtra("Index",approvedSubjects.get(i))
                startActivity(intent)
            }
            contenedor.addView(boton)
        }
    }

    public fun loadInformation(indexGrade: Int){

        val examsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post

                    if(postSnapshot.child("grade").getValue(Int::class.java)!! == indexGrade) {
                        //var grade = postSnapshot
                        gradeTest.id = postSnapshot.child("id").getValue(String::class.java)!!
                        gradeTest.grade = postSnapshot.child("grade").getValue(Int::class.java)!!
                        var i = 0
                        for (postSnapshot in postSnapshot.child("subjects").children) {
                            var j = 0

                            var insertExam =  StructExam("","", ArrayList<StructQuestion>())
                            //gradeTest.subjects.add(postSnapshot.getValue(StructExam::class.java)!!)
                            gradeTest.subjects.add(insertExam)
                            gradeTest.subjects[i].id = postSnapshot.child("id").getValue(String::class.java)!!
                            gradeTest.subjects[i].name = postSnapshot.child("name").getValue(String::class.java)!!
                            for (postSnapshot in postSnapshot.child("questions").children) {

                                var insertQuestion = StructQuestion("",0, StructOptions("","","",""))
                                gradeTest.subjects[i].questions.add(insertQuestion)
                                gradeTest.subjects[i].questions[j].options.optionOne = postSnapshot.child("options").child("optionOne")
                                    .getValue(String::class.java)!!
                                gradeTest.subjects[i].questions[j].options.optionTwo = postSnapshot.child("options").child("optionTwo")
                                    .getValue(String::class.java)!!
                                gradeTest.subjects[i].questions[j].options.optionThree = postSnapshot.child("options").child("optionThree")
                                        .getValue(String::class.java)!!
                                gradeTest.subjects[i].questions[j].options.optionFour = postSnapshot.child("options").child("optionFour")
                                        .getValue(String::class.java)!!
                                gradeTest.subjects[i].questions[j].question = postSnapshot.child("question").getValue(String::class.java)!!
                                gradeTest.subjects[i].questions[j].correctAnswer = postSnapshot.child("correctAnswer").getValue(Int::class.java)!!


                                //Toast.makeText(applicationContext, "$i "+ "$j", Toast.LENGTH_LONG).show()
                                j++
                            }

                            i++
                        }
                    }// PONLO AQUÍ

                }
                // AQUÍ SE IMPRIME EL EXAMEN YA CON TODA LA INFO
                // Toast.makeText(applicationContext, "$gradeTest", Toast.LENGTH_LONG).show()

                printAllExams(contenedor1, contenedor2)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        database.addValueEventListener(examsListener);
    }

}