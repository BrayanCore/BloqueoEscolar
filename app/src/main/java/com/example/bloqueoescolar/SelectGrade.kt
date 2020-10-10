package com.example.bloqueoescolar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.select_grade.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SelectGrade : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "SelectGradeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_grade)

        first_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade",1)
            startActivity(intent)
        }
        auth = Firebase.auth
        val user = auth.currentUser
        Log.d(TAG, "Current user email: ${user?.email}")
    }

        second_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade",2)
            startActivity(intent)
        }

        third_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade",3)
            startActivity(intent)
        }

        fourth_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade",4)
            startActivity(intent)
        }

        fifth_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade",5)
            startActivity(intent)
        }

        sixth_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade",6)
            startActivity(intent)
        }

    }

}