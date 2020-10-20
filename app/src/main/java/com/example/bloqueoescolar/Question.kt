package com.example.bloqueoescolar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_question.*

class Question : AppCompatActivity() {

    var optionSelected = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val index = intent.getStringExtra("Index")

        Toast.makeText(this,
            "$index", Toast.LENGTH_LONG
        ).show()

        // AÑADIR EVENTO PARA CUANDO SE CLIQUEA UNA OPCION
        first_clicked.setOnClickListener {
            optionSelected = 1;
            Toast.makeText(this,
                "$optionSelected", Toast.LENGTH_LONG
            ).show()
        }

        second_clicked.setOnClickListener {
            optionSelected = 2;
            Toast.makeText(this,
                "$optionSelected", Toast.LENGTH_LONG
            ).show()
        }

        third_clicked.setOnClickListener {
            optionSelected = 3;
            Toast.makeText(this,
                "$optionSelected", Toast.LENGTH_LONG
            ).show()
        }

        fourth_clicked.setOnClickListener {
            optionSelected = 4;
            Toast.makeText(this,
                "$optionSelected", Toast.LENGTH_LONG
            ).show()
        }

        // EVENTO PARA CUANDO SE ENVIA LA RESPUESTA
        submit_exam_btn.setOnClickListener {
            answer()
        }

    }

    fun answer(){
        Toast.makeText(this,
            "$optionSelected", Toast.LENGTH_LONG
        ).show()
    }

}