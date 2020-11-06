package com.example.bloqueoescolar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bloqueoescolar.domain.struct.StructExam
import com.example.bloqueoescolar.domain.struct.StructGrade
import com.example.bloqueoescolar.domain.struct.StructOptions
import com.example.bloqueoescolar.domain.struct.StructQuestion
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.exams_options.*

class Exams : AppCompatActivity() {

    companion object {
        private const val TAG = "Exams"
    }

    private var actualGrade: StructGrade = StructGrade();
    private lateinit var gradeId: String;

    private lateinit var gradeReference: DatabaseReference;
    private lateinit var gradeListener: ValueEventListener;

    private lateinit var contenedor1: LinearLayout
    private lateinit var contenedor2: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exams_options)

        //Se obtiene el grado actual
        gradeId = intent.getStringExtra("grade") as String

        //Se referencia a la base de datos grados
        gradeReference = FirebaseDatabase.getInstance().getReference("Grados/${gradeId}")
        getSubjectsGrade()

        contenedor1 = findViewById(R.id.AvailableExams)
        contenedor2 = findViewById(R.id.Layout)

        // Crear examen activity
        create_exam.setOnClickListener {
            val intent = Intent(applicationContext, AddQuestion::class.java)
            intent.putExtra("grade", actualGrade)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun getSubjectsGrade() {

        gradeListener = object : ValueEventListener {
            override fun onDataChange(gradeSnapshot: DataSnapshot) {

                //Se guardan datos del grado
                val gradeDb = StructGrade()
                gradeDb.id = gradeSnapshot.child("id").getValue(String::class.java)!!
                gradeDb.grade = gradeSnapshot.child("grade").getValue(Int::class.java)!!

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
                    gradeDb.subjects.add(exam) // Se agregan examenes al grado o grupo
                }

                actualGrade = gradeDb //Se actualiza la informacion
                printAllExams(contenedor1)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        gradeReference.addValueEventListener(gradeListener)
    }

    /**
     * Funcion para imprimir todos los examenes
     */
    fun printAllExams(contenedor: LinearLayout) {
        contenedor.removeAllViews()
        for (exam in actualGrade.subjects!!) {

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