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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.exams_options.*
import kotlin.collections.ArrayList

class Exams : AppCompatActivity() {

    companion object {
        private const val TAG = "Exams"
    }

    private var actualGrade: StructGrade = StructGrade();
    private lateinit var gradeId: String;

    private lateinit var gradeReference: DatabaseReference;
    private lateinit var gradeListener: ValueEventListener;

    var examsApproved = ArrayList<String>()

    private lateinit var pendingContainer: LinearLayout
    private lateinit var approvedContainer: LinearLayout

    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    val databaseUser = FirebaseDatabase.getInstance().getReference("usuario").child(
        currentFirebaseUser!!.uid
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exams_options)

        // Se obtienen examenes aprobados del usuario.
        examsApproved = intent.getStringArrayListExtra("examsApproved")!!

        //Se obtiene el grado actual
        gradeId = intent.getStringExtra("grade") as String

        // Se referencia a la base de datos grados.
        gradeReference = FirebaseDatabase.getInstance().getReference("Grados/${gradeId}")
        getSubjectsGrade()

        // Se obtienen los contenedores para mostrar los examenes.
        pendingContainer = findViewById(R.id.AvailableExams)
        approvedContainer = findViewById(R.id.Layout)
    }

    override fun onStart() {
        super.onStart()

        // Boton para crear examen
        create_exam.setOnClickListener {
            val intent = Intent(applicationContext, AddQuestion::class.java)
            intent.putExtra("grade", actualGrade)
            startActivity(intent)
        }
    }

    fun examsApprovedOfUser() {
        examsApproved = ArrayList();
        databaseUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Getting the string value of that node

                for (postSnapshot in dataSnapshot.child("IDs").children) {

                    var ID = postSnapshot.getValue(String::class.java)
                    //Toast.makeText(applicationContext,"$ID", Toast.LENGTH_LONG).show()
                    examsApproved.add(ID!!)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }


        })
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
                        question.question =
                            questionSnapshot.child("question").getValue(String::class.java)!!
                        question.correctAnswer =
                            questionSnapshot.child("correctAnswer").getValue(Int::class.java)!!

                        // Options
                        val options =
                            questionSnapshot.child("options").getValue(StructOptions::class.java)!!
                        question.options = options

                        exam.questions.add(question) // Se agregan preguntas al examen
                    }
                    gradeDb.subjects.add(exam) // Se agregan examenes al grado o grupo
                }

                actualGrade = gradeDb //Se actualiza la informacion
                printAllExams() // Se muestran los examenes en pantalla
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
    fun printAllExams() {
        // Se limpian los examenes ya mostrados para no acumular mas.
        pendingContainer.removeAllViews()
        approvedContainer.removeAllViews()

        // Se recorren los examenes al grado correspondiente.
        for (exam in actualGrade.subjects!!) {
            val found: Boolean = examsApproved.contains(exam.id)

            val boton = Button(applicationContext)
            boton.text = exam.name

            if (found) { // Examenes Aprobados
                boton.setBackgroundResource(R.drawable.boton_enviar)
                boton.setOnClickListener {
                    Toast.makeText(applicationContext, "Exámen Aprobado", Toast.LENGTH_LONG)
                        .show()
                }
                approvedContainer.addView(boton)

            } else { // Examenes Pendientes
                boton.setBackgroundResource(R.drawable.boton_respuestas)
                boton.setOnClickListener() {
                    val intent = Intent(applicationContext, Question::class.java)
                    intent.putExtra("questions", exam.questions)
                    intent.putExtra("id", exam.id)
                    intent.putExtra("indexGrade", actualGrade.id)
                    startActivity(intent)
                    finish()
                }
                pendingContainer.addView(boton)
            }
        }
    }

    fun changeToQuestion(view: View) {
        Toast.makeText(applicationContext, "Hola", Toast.LENGTH_LONG).show()
        val intent = Intent(view.context, Grades::class.java)
        startActivity(intent)
        finish()
    }

}