package com.example.bloqueoescolar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_question.*
import kotlinx.android.synthetic.main.send_exam_dialog.view.*
import kotlin.random.Random

class AddQuestion : AppCompatActivity() {

    var Exam = StructExam(
        "",
        0,
        ArrayList<StructQuestion>()
    )

    var stop = false;
    var numberCorrect = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_question)

        add_question.setOnClickListener {

            ValidateFields();
            if(stop){
                return@setOnClickListener
            }


            var numbers =  ArrayList<Int>()
            var numberToWant = 4

            do {
                var next = (1..4).random()
                if(!numbers.contains(next)){
                    numbers.add(next)
                }
            }while (numbers.size < numberToWant)

            var answers = StructOptions("","","","")

            for(i in 0 until numbers.size){

                when(i){
                    0 -> when(numbers[i]){
                        1 -> {
                            answers.optionOne = answer1.text.toString()
                            numberCorrect = i+1
                        }
                        2 -> answers.optionOne =  answer2.text.toString()
                        3 -> answers.optionOne = answer3.text.toString()
                        4 -> answers.optionOne =  answer4.text.toString()
                        else -> Toast.makeText(this, "Invalid Number", Toast.LENGTH_LONG).show()
                    }
                    1 -> when(numbers[i]){
                        1 -> {
                            answers.optionTwo = answer1.text.toString()
                            numberCorrect = i+1
                        }
                        2 -> answers.optionTwo =  answer2.text.toString()
                        3 -> answers.optionTwo = answer3.text.toString()
                        4 -> answers.optionTwo =  answer4.text.toString()
                        else -> Toast.makeText(this, "Invalid Number", Toast.LENGTH_LONG).show()
                    }
                    2 -> when(numbers[i]){
                        1 -> {
                            answers.optionThree = answer1.text.toString()
                            numberCorrect = i+1
                        }
                        2 -> answers.optionThree =  answer2.text.toString()
                        3 -> answers.optionThree = answer3.text.toString()
                        4 -> answers.optionThree =  answer4.text.toString()
                        else -> Toast.makeText(this, "Invalid Number", Toast.LENGTH_LONG).show()
                    }
                    3 -> when(numbers[i]){
                        1 -> {
                            answers.optionFour = answer1.text.toString()
                            numberCorrect = i+1
                        }
                        2 -> answers.optionFour =  answer2.text.toString()
                        3 -> answers.optionFour = answer3.text.toString()
                        4 -> answers.optionFour =  answer4.text.toString()
                        else -> Toast.makeText(this, "Invalid Number", Toast.LENGTH_LONG).show()
                    }
                    else -> Toast.makeText(this, "Invalid Number", Toast.LENGTH_LONG).show()
                }

            }

            Toast.makeText(applicationContext, "$answers", Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "$numberCorrect", Toast.LENGTH_LONG).show()

            val que1 = StructQuestion(
                0,
                description.text.toString(),
                numberCorrect,
                answers
            )

            addQuestionToExam(que1)

        }

        // OPEN DIALOG
        send_exam_btn.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.send_exam_dialog, null);

            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("ENVIAR EXAMEN")

            val mAlertDialog = mBuilder.show()

            mDialogView.send.setOnClickListener {
                mAlertDialog.dismiss()

                val name = mDialogView.exam_name.text.toString()
                Toast.makeText(applicationContext, "$name", Toast.LENGTH_LONG).show()
                Exam.name = name
                sendExam()
            }

            mDialogView.cancel.setOnClickListener {
                mAlertDialog.dismiss()
                Toast.makeText(applicationContext, "CANCELADO", Toast.LENGTH_LONG).show()
            }

        }

    }

    fun addQuestionToExam(question: StructQuestion){

        /*var randomNumber =  (1..4).random()
        Toast.makeText(applicationContext, "$randomNumber", Toast.LENGTH_LONG).show()*/

        Exam.questions.add(question)
        description.text.clear()
        answer1.text!!.clear()
        answer2.text!!.clear()
        answer3.text!!.clear()
        answer4.text!!.clear()

    }

    fun sendExam(){

        Toast.makeText(applicationContext, "$Exam", Toast.LENGTH_LONG).show()

    }

    fun ValidateFields(){

        if(description.text.isEmpty() == true || answer1.text.isNullOrEmpty() == true || answer2.text.isNullOrEmpty() == true || answer3.text.isNullOrEmpty() == true || answer4.text.isNullOrEmpty() == true){
            Toast.makeText(applicationContext, "COMPLETA TODOS LOS CAMPOS", Toast.LENGTH_LONG).show()
            stop = true
        }else{
            stop = false
        }

    }

}