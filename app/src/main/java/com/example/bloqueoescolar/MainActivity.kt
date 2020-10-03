package com.example.bloqueoescolar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val approvedSubjects = arrayOf("Biología", "Matematicas", "Química", "Español", "Ética", "Cursiva", "Ed.Física", "Historia")
    val pendingSubjects = arrayOf("Biología", "Matematicas", "Química", "Español", "Ética", "Cursiva", "Ed.Física", "Historia")
    var j = 0;

    private lateinit var contenedor1: LinearLayout
    private lateinit var contenedor2: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exams_options)

        contenedor1 = findViewById(R.id.AvailableExams)
        contenedor2 = findViewById(R.id.Layout)

        PendingExams(pendingSubjects.size, contenedor1)
        ApprovedExams(approvedSubjects.size,contenedor2)

    }

    fun ApprovedExams(elementos: Int, contenedor: LinearLayout) {
        for (i in 0 until elementos) {
            //creando un objeto de la clase button
            val boton = Button(applicationContext)
            //Personalizando botones
            boton.text = approvedSubjects.get(i)
            boton.setBackgroundResource(R.drawable.boton_respuestas)
            boton.setOnClickListener(){
                val intent = Intent(applicationContext, Question::class.java)
                startActivity(intent)
                finish()
            }
            contenedor.addView(boton)
        }
    }

    fun PendingExams(elementos: Int, contenedor: LinearLayout) {
        for (i in 0 until elementos) {
            //creando un objeto de la clase button
            val boton = Button(applicationContext)
            //Personalizando botones
            boton.text = pendingSubjects.get(i)
            boton.setBackgroundResource(R.drawable.boton_enviar)
            contenedor.addView(boton)
        }
    }

    fun changeToQuestion(view: View){
        Toast.makeText(applicationContext, "Hola", Toast.LENGTH_LONG).show()
        val intent = Intent(view.context, SelectGrade::class.java)
        startActivity(intent)
        finish()
    }

}