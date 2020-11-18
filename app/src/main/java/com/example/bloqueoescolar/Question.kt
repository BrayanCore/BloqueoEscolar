package com.example.bloqueoescolar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bloqueoescolar.domain.struct.StructExam
import com.example.bloqueoescolar.domain.struct.StructQuestion
import kotlinx.android.synthetic.main.activity_question.*

class Question : AppCompatActivity() {

    var optionSelected = 0; // Indica la opcion seleccionada
    var index = 0; // Indica el numero de pregunta actual
    var score = 0 // Indica cuántos aciertos tuvo

    var correctCurrentAnswer = 0

    var currentExam = StructExam("","", ArrayList<StructQuestion>())
    var answersCorrects = ArrayList<Int>()
    var indexGrade = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        //var lista: ArrayList<StructQuestion> = intent.getSerializableExtra("questions") as ArrayList<StructQuestion>
        currentExam.questions = intent.getSerializableExtra("questions") as ArrayList<StructQuestion>

        currentExam.id = intent.getStringExtra("id")!!
        indexGrade = intent.getIntExtra("indexGrade",0)

        changeQuestion();
    }

    override fun onStart() {
        super.onStart()

        // AÑADIR EVENTO PARA CUANDO SE CLIQUEA UNA OPCION
        first_clicked.setOnClickListener {
            optionSelected = 1;
            changeColor(optionSelected)
        }

        second_clicked.setOnClickListener {
            optionSelected = 2;
            changeColor(optionSelected)
        }

        third_clicked.setOnClickListener {
            optionSelected = 3;
            changeColor(optionSelected)
        }

        fourth_clicked.setOnClickListener {
            optionSelected = 4;
            changeColor(optionSelected)
        }

        // EVENTO PARA CUANDO SE ENVIA LA RESPUESTA
        submit_exam_btn.setOnClickListener {
            answer(optionSelected)
        }

    }

    fun changeQuestion(){

            clearFields()
            //Se agrega texto de pregunta
            descriptionProblem.text = currentExam.questions[index].question

            //Se agrega el texto de respuestas
            first_clicked.text = currentExam.questions[index].options.optionOne
            second_clicked.text = currentExam.questions[index].options.optionTwo
            third_clicked.text = currentExam.questions[index].options.optionThree
            fourth_clicked.text = currentExam.questions[index].options.optionFour

            // Se asigna la respuesta correcta
            optionSelected = 0

    }

    fun answer(i: Int){

        if(optionSelected != 0){
            if(index < currentExam.questions.size){
                if(i == currentExam.questions[index].correctAnswer){
                    score++
                    answersCorrects.add((index+1))
                }

                index++
                if (index == (currentExam.questions.size-1)){
                    submit_exam_btn.text = "ENVIAR EXÁMEN"
                    submit_exam_btn.setOnClickListener {
                        if (optionSelected != 0){
                            /*Toast.makeText(this,
                                "EXÁMEN ENVIADO", Toast.LENGTH_LONG
                            ).show()*/
                            if(optionSelected == currentExam.questions[index].correctAnswer){
                                score++
                                answersCorrects.add((index+1))
                            }

                            val intent = Intent(applicationContext, Score::class.java)
                            intent.putExtra("idExam", currentExam.id)
                            intent.putExtra("score", score)
                            intent.putExtra("indexGrade", indexGrade)
                            intent.putExtra("reactives", currentExam.questions.size)
                            intent.putIntegerArrayListExtra("answersCorrects", answersCorrects)
                            startActivity(intent)
                            finish()
                        }else{
                            selectOption()
                        }
                    }
                    changeQuestion()
                }else if(index < currentExam.questions.size){
                    changeQuestion()
                }
            }
        }else{
            selectOption()
        }
    }

    fun selectOption(){
        Toast.makeText(this,
            "TIENES QUE SELECCIONAR UNA OPCIÓN", Toast.LENGTH_LONG
        ).show()
    }

    fun changeColor(i: Int){

        when(i) {
            1 -> {
                first_clicked.setBackgroundResource(R.drawable.c_bluelight)
                second_clicked.setBackgroundResource(R.drawable.c_redond)
                third_clicked.setBackgroundResource(R.drawable.c_redond)
                fourth_clicked.setBackgroundResource(R.drawable.c_redond)
            }
            2 -> {
                first_clicked.setBackgroundResource(R.drawable.c_redond)
                second_clicked.setBackgroundResource(R.drawable.c_bluelight)
                third_clicked.setBackgroundResource(R.drawable.c_redond)
                fourth_clicked.setBackgroundResource(R.drawable.c_redond)
            }
            3 -> {
                first_clicked.setBackgroundResource(R.drawable.c_redond)
                second_clicked.setBackgroundResource(R.drawable.c_redond)
                third_clicked.setBackgroundResource(R.drawable.c_bluelight)
                fourth_clicked.setBackgroundResource(R.drawable.c_redond)
            }
            4 -> {
                first_clicked.setBackgroundResource(R.drawable.c_redond)
                second_clicked.setBackgroundResource(R.drawable.c_redond)
                third_clicked.setBackgroundResource(R.drawable.c_redond)
                fourth_clicked.setBackgroundResource(R.drawable.c_bluelight)
            }
            else ->{

            }
        }

    }

    fun clearFields(){
        first_clicked.setBackgroundResource(R.drawable.c_redond)
        second_clicked.setBackgroundResource(R.drawable.c_redond)
        third_clicked.setBackgroundResource(R.drawable.c_redond)
        fourth_clicked.setBackgroundResource(R.drawable.c_redond)
    }

}