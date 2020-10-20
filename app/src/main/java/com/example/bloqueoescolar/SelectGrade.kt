package com.example.bloqueoescolar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.select_grade.*

class SelectGrade : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "SelectGradeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_grade)

        auth = Firebase.auth
        val user = auth.currentUser
        Log.d(TAG, "Current user email: ${user?.email}")


        first_grade.setOnClickListener {
            /*val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("PRUEBAS")

            myRef.setValue(user)*/

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

}