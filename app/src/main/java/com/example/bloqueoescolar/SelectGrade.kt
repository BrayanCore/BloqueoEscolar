package com.example.bloqueoescolar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SelectGrade : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_grade)
    }

    fun changeLayout(view: View){
        val intent = Intent(view.context, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}