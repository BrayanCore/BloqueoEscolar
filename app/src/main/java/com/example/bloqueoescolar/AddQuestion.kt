package com.example.bloqueoescolar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_question.*
import kotlinx.android.synthetic.main.send_exam_dialog.view.*
import kotlin.random.Random

class AddQuestion : AppCompatActivity() {

    var Exam = StructExam(
        "",
        "",
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

        /*
        val database = FirebaseDatabase.getInstance().getReference("Exámenes")
        val examID = database.push().key

        Exam.id = examID!!

        val examen = Exam

        database.child(examID).setValue(examen).addOnCompleteListener{
            Toast.makeText(applicationContext, "User saved successfully", Toast.LENGTH_LONG).show()
        }
        */

        //createGrades()

    }

    fun ValidateFields(){

        if(description.text.isEmpty() == true || answer1.text.isNullOrEmpty() == true || answer2.text.isNullOrEmpty() == true || answer3.text.isNullOrEmpty() == true || answer4.text.isNullOrEmpty() == true){
            Toast.makeText(applicationContext, "COMPLETA TODOS LOS CAMPOS", Toast.LENGTH_LONG).show()
            stop = true
        }else{
            stop = false
        }

    }

    fun createGrades(){

        val database = FirebaseDatabase.getInstance().getReference("Grados")
        for (j in 1 until 7){

            when(j) {
                1 -> {
                    var grade1 = StructGrade("",1,ArrayList<StructExam>())
                    val grade1ID = database.push().key
                    grade1.id = grade1ID!!

                    database.child(grade1ID).setValue(grade1).addOnCompleteListener{
                        Toast.makeText(applicationContext, "Grado escolar creado", Toast.LENGTH_SHORT).show()
                    }
                    val databaseExam = FirebaseDatabase.getInstance().getReference("Grados").child(grade1ID).child("subjects")
                    val exam1ID = databaseExam.push().key
                    Exam.id = exam1ID!!
                    databaseExam.child(exam1ID).setValue(Exam).addOnCompleteListener {
                        Toast.makeText(applicationContext, "Exámen agregado", Toast.LENGTH_SHORT).show()
                    }
                }
                2 -> {
                    var grade2 = StructGrade("",2,ArrayList<StructExam>())
                    val grade2ID = database.push().key
                    grade2.id = grade2ID!!
                    database.child(grade2ID).setValue(grade2).addOnCompleteListener{
                        Toast.makeText(applicationContext, "Grado escolar creado", Toast.LENGTH_SHORT).show()
                    }
                    val databaseExam = FirebaseDatabase.getInstance().getReference("Grados").child(grade2ID).child("subjects")
                    val exam2ID = databaseExam.push().key
                    Exam.id = exam2ID!!
                    databaseExam.child(exam2ID).setValue(Exam).addOnCompleteListener {
                        Toast.makeText(applicationContext, "Exámen agregado", Toast.LENGTH_SHORT).show()
                    }
                }
                3 -> {
                    var grade3 = StructGrade("",3,ArrayList<StructExam>())
                    val grade3ID = database.push().key
                    grade3.id = grade3ID!!
                    database.child(grade3ID).setValue(grade3).addOnCompleteListener{
                        Toast.makeText(applicationContext, "Grado escolar creado", Toast.LENGTH_SHORT).show()
                    }
                    val databaseExam = FirebaseDatabase.getInstance().getReference("Grados").child(grade3ID).child("subjects")
                    val exam3ID = databaseExam.push().key
                    Exam.id = exam3ID!!
                    databaseExam.child(exam3ID).setValue(Exam).addOnCompleteListener {
                        Toast.makeText(applicationContext, "Exámen agregado", Toast.LENGTH_SHORT).show()
                    }
                }
                4 -> {
                    var grade4 = StructGrade("",4,ArrayList<StructExam>())
                    val grade4ID = database.push().key
                    grade4.id = grade4ID!!
                    database.child(grade4ID).setValue(grade4).addOnCompleteListener{
                        Toast.makeText(applicationContext, "Grado escolar creado", Toast.LENGTH_SHORT).show()
                    }
                    val databaseExam = FirebaseDatabase.getInstance().getReference("Grados").child(grade4ID).child("subjects")
                    val exam4ID = databaseExam.push().key
                    Exam.id = exam4ID!!
                    databaseExam.child(exam4ID).setValue(Exam).addOnCompleteListener {
                        Toast.makeText(applicationContext, "Exámen agregado", Toast.LENGTH_SHORT).show()
                    }
                }
                5 -> {
                    var grade5 = StructGrade("",5,ArrayList<StructExam>())
                    val grade5ID = database.push().key
                    grade5.id = grade5ID!!
                    database.child(grade5ID).setValue(grade5).addOnCompleteListener{
                        Toast.makeText(applicationContext, "Grado escolar creado", Toast.LENGTH_SHORT).show()
                    }
                    val databaseExam = FirebaseDatabase.getInstance().getReference("Grados").child(grade5ID).child("subjects")
                    val exam5ID = databaseExam.push().key
                    Exam.id = exam5ID!!
                    databaseExam.child(exam5ID).setValue(Exam).addOnCompleteListener {
                        Toast.makeText(applicationContext, "Exámen agregado", Toast.LENGTH_SHORT).show()
                    }
                }
                6 -> {
                    var grade6 = StructGrade("",6,ArrayList<StructExam>())
                    val grade6ID = database.push().key
                    grade6.id = grade6ID!!
                    database.child(grade6ID).setValue(grade6).addOnCompleteListener{
                        Toast.makeText(applicationContext, "Grado escolar creado", Toast.LENGTH_SHORT).show()
                    }
                    val databaseExam = FirebaseDatabase.getInstance().getReference("Grados").child(grade6ID).child("subjects")
                    val exam6ID = databaseExam.push().key
                    Exam.id = exam6ID!!
                    databaseExam.child(exam6ID).setValue(Exam).addOnCompleteListener {
                        Toast.makeText(applicationContext, "Exámen agregado", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        }

    }

}