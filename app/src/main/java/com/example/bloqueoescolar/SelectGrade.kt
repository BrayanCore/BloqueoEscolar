package com.example.bloqueoescolar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
        auth = Firebase.auth
        val user = auth.currentUser
        Log.d(TAG, "Current user email: ${user?.email}")
    }

    fun changeLayout(view: View){
        val intent = Intent(view.context, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}