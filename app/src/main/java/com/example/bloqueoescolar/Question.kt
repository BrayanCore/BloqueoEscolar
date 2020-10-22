package com.example.bloqueoescolar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bloqueoescolar.domain.struct.StructQuestion
import kotlinx.android.synthetic.main.activity_question.*

class Question : AppCompatActivity() {

    var optionSelected = 0;
    var index = 0; // Indica el numero de pregunta actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        var lista: ArrayList<StructQuestion> = intent.getSerializableExtra("questions") as ArrayList<StructQuestion>
        index = intent.getIntExtra("index", 0)

        val question = lista.get(index) // Pregunta
        val options = question.options // Opciones multiples

        //Se agrega texto de pregunta
        descriptionProblem.text = question.question

        //Se agrega el texto de respuestas
        first_clicked.text = options.optionOne
        second_clicked.text = options.optionTwo
        third_clicked.text = options.optionThree
        fourth_clicked.text = options.optionFour

        // AÑADIR EVENTO PARA CUANDO SE CLIQUEA UNA OPCION
        first_clicked.setOnClickListener {
            optionSelected = 1;
        }

        second_clicked.setOnClickListener {
            optionSelected = 2;
        }

        third_clicked.setOnClickListener {
            optionSelected = 3;
        }

        fourth_clicked.setOnClickListener {
            optionSelected = 4;
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