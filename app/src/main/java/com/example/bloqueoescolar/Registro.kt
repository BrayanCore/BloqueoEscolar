package com.example.bloqueoescolar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.emergent_screen.*

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.emergent_screen)

        btn_start.setOnClickListener(){
            val printName = name.text.toString()
            val printEmail =  email.text.toString()
            val printPass =  password.text.toString()
            val printConfPass = confirm_password.text.toString()
            Toast.makeText(this,
                "$printName\n"+"$printEmail\n"+"$printPass\n"+"$printConfPass", Toast.LENGTH_SHORT
            ).show()
        }
    }

}