package com.example.bloqueoescolar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_score.*

class Score : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        test_again.setOnClickListener {
            val intent = Intent(this, Exams::class.java)
            startActivity(intent)
            finish()
        }

    }
}