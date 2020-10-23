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

        first_clicked.text = "1"
        second_clicked.text = "2"
        third_clicked.text = "3"
        fourth_clicked.text = "4"
        descriptionProblem.text = "Número"

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
        cleanFields()
    }

    fun changeColor(i: Int){

        when(i) {
            1 -> {
                first_clicked.background
                second_clicked.background
                third_clicked.background
                fourth_clicked.background
            }
            2 -> {

            }
            3 -> {

            }
            4 -> {

            }
            else ->{

            }
        }

    }

    fun cleanFields(){
        first_clicked.text = ""
        second_clicked.text = ""
        third_clicked.text = ""
        fourth_clicked.text = ""
        descriptionProblem.text = ""
    }

}