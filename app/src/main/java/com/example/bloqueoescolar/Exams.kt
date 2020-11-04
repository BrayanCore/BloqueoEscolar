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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.exams_options.*
import kotlin.collections.ArrayList

class Exams : AppCompatActivity() {

    //val approvedSubjects = arrayOf("Biología", "Matematicas", "Química", "Español", "Ética", "Cursiva", "Ed.Física", "Historia")
    //val pendingSubjects = arrayOf("Biología", "Matematicas", "Química", "Español", "Ética", "Cursiva", "Ed.Física", "Historia")
    var grade = StructGrade()
    val database = FirebaseDatabase.getInstance().getReference("Grados")

    private lateinit var contenedor1: LinearLayout
    private lateinit var contenedor2: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exams_options)

        //Se obtiene el grado actual
        grade = intent.getSerializableExtra("grade") as StructGrade

        contenedor1 = findViewById(R.id.AvailableExams)
        contenedor2 = findViewById(R.id.Layout)

        val index = intent.getIntExtra("Grade",100)

        // Crear examen activity
        create_exam.setOnClickListener {
            val intent = Intent(applicationContext, AddQuestion::class.java)
            intent.putExtra("grade", grade)
            startActivity(intent)
        }

        printAllExams(contenedor1)
    }

    override fun onStart() {
        super.onStart()
    }

    /**
     * Funcion para imprimir todos los examenes
     */
    fun printAllExams(contenedor: LinearLayout) {
        for (exam in grade.subjects!!) {

            val boton = Button(applicationContext)
            boton.text = exam.name
            boton.setBackgroundResource(R.drawable.boton_respuestas)

            // Llamamos a la funcion on click
            boton.setOnClickListener(){
                val intent = Intent(applicationContext, Question::class.java)
                intent.putExtra("questions", exam.questions)
                intent.putExtra("index", 0)
                startActivity(intent)
            }

            // Se agrega el boton a la vista
            contenedor.addView(boton)
        }
    }

    fun changeToQuestion(view: View){
        Toast.makeText(applicationContext, "Hola", Toast.LENGTH_LONG).show()
        val intent = Intent(view.context, Grades::class.java)
        startActivity(intent)
        finish()
    }

    /*@Deprecated("Funcion no utilizada")
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
    }*/

    /*@Deprecated("Funcion no utilizada")
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
    }*/
}