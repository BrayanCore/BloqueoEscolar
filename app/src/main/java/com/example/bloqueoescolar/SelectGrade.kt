package com.example.bloqueoescolar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bloqueoescolar.domain.struct.StructExam
import com.example.bloqueoescolar.domain.struct.StructGrade
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.select_grade.*

class SelectGrade : AppCompatActivity() {

    companion object {
        private const val TAG = "SelectGradeActivity"
    }

    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    val databaseUser = FirebaseDatabase.getInstance().getReference("usuario").child(
        currentFirebaseUser!!.uid
    )

    var examsApproved = ArrayList<String>()

    var database = FirebaseDatabase.getInstance().getReference("Grados")
    var grades: ArrayList<StructGrade> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_grade)

        examsApprovedOfUser() // Obtiene examenes aprovados del usuario
        getGrades() // Obtiene los grados
    }

    private fun getGrades() {
        grades = ArrayList()
        val gradesListener = object : ValueEventListener {
            override fun onDataChange(gradesSnaspshot: DataSnapshot) {
                for (gradeSnapshot in gradesSnaspshot.children) { // Se guardan los grados
                    val grade = StructGrade()

                    grade.id = gradeSnapshot.child("id").getValue(String::class.java)!!
                    grade.grade = gradeSnapshot.child("grade").getValue(Int::class.java)!!

                    grades.add(grade) //Se agrega objeto grado a la lista de grados
                }

                // Fun para imprimir los botones
                showGrades()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        database.addValueEventListener(gradesListener)
    }

    /**
     * Funcion para setear propiedades a los botones existentes.
     *
     */
    private fun showGrades() {

        for(i in 0 until grades.size) {
            when(i) {
                0 -> {
                    first_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i).id)
                        intent.putStringArrayListExtra("examsApproved", examsApproved)
                        startActivity(intent)
                    }
                }

                1 -> {
                    second_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i).id)
                        intent.putStringArrayListExtra("examsApproved", examsApproved)
                        startActivity(intent)
                    }
                }

                2 -> {
                    third_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i).id)
                        intent.putStringArrayListExtra("examsApproved", examsApproved)
                        startActivity(intent)
                    }
                }

                3 -> {
                    fourth_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i).id)
                        intent.putStringArrayListExtra("examsApproved", examsApproved)
                        startActivity(intent)
                    }
                }

                4-> {
                    fifth_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i).id)
                        intent.putStringArrayListExtra("examsApproved", examsApproved)
                        startActivity(intent)
                    }
                }

                5 -> {
                    sixth_grade.setOnClickListener {
                        val intent = Intent(applicationContext, Exams::class.java)
                        intent.putExtra("grade", grades.get(i).id)
                        intent.putStringArrayListExtra("examsApproved", examsApproved)
                        startActivity(intent)
                    }
                }

                else -> {
                    Log.e(TAG, "Opcion no disponible.")
                }
            }
        }
    }

    fun examsApprovedOfUser() {

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

}