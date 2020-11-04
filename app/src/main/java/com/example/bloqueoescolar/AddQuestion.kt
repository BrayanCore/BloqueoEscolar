package com.example.bloqueoescolar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bloqueoescolar.domain.struct.StructExam
import com.example.bloqueoescolar.domain.struct.StructGrade
import com.example.bloqueoescolar.domain.struct.StructOptions
import com.example.bloqueoescolar.domain.struct.StructQuestion
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_question.*
import kotlinx.android.synthetic.main.send_exam_dialog.view.*

class AddQuestion : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    var exam: StructExam = StructExam()
    var grade: StructGrade = StructGrade()

    var stop = false;
    var numberCorrect = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_question)

        database = Firebase.database.reference

        if(grade.id.isNullOrEmpty()){
            grade = intent.getSerializableExtra("grade") as StructGrade
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
                exam.name = name
                sendExam()
            }

            mDialogView.cancel.setOnClickListener {
                mAlertDialog.dismiss()
                Toast.makeText(applicationContext, "CANCELADO", Toast.LENGTH_LONG).show()
            }

        }

    }

    override fun onStart() {
        super.onStart()

        // Se agrega una pregunta al examen
        add_question.setOnClickListener {
            addQuestion()
        }
    }

    private fun addQuestion() {
        if (validateFields()) { // Valida que los campos sean correctos

            var numbers =  ArrayList<Int>()
            var numberToWant = 4

            do {
                var next = (1..4).random()
                if(!numbers.contains(next)){
                    numbers.add(next)
                }
            }while (numbers.size < numberToWant)

            var answers = StructOptions()

            for(i in 0 until numbers.size){

                when(i){
                    0 -> when(numbers[i]) {
                        1 -> {
                            answers.optionOne = answer1.text.toString()
                            numberCorrect = i+1
                        }

                        2 -> answers.optionOne =  answer2.text.toString()

                        3 -> answers.optionOne = answer3.text.toString()

                        4 -> answers.optionOne =  answer4.text.toString()

                        else -> Toast.makeText(this, "Invalid Number", Toast.LENGTH_LONG).show()
                    }

                    1 -> when(numbers[i]) {
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

                    3 -> when(numbers[i]) {
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

                } //End Switch

            } //End for

            // Se crea el objeto de la pregunta
            val questionAdded = StructQuestion(
                description.text.toString(),
                numberCorrect,
                answers
            )

            // Se añade la pregunta al examen
            addQuestionToExam(questionAdded)
        }
    }

    fun addQuestionToExam(questionAdded: StructQuestion){
        exam.questions.add(questionAdded)

        clearFields()
    }


    fun sendExam() {
        // Se establece referencia a tabla de examenes del grado actual
        database = FirebaseDatabase.getInstance().getReference("Grados/${grade.id}/subjects")

        // Se crea el id del examen
        val examID = database.push().key
        exam.id = examID!!

        // Se guarda el examen
        database.child(examID).setValue(exam).addOnCompleteListener {
            Toast.makeText(applicationContext, "Se ha guardado el examen correctamente.",
                Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Funcion para limpiar los campos
     */
    private fun clearFields() {
        description.text.clear()
        answer1.text!!.clear()
        answer2.text!!.clear()
        answer3.text!!.clear()
        answer4.text!!.clear()
    }

    /**
     * Valida los campos para agregar una nueva pregunta
     */
    private fun validateFields() : Boolean {

        if (description.text.isEmpty() || answer1.text.isNullOrEmpty()
            || answer2.text.isNullOrEmpty() || answer3.text.isNullOrEmpty()
            || answer4.text.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "COMPLETA TODOS LOS CAMPOS", Toast.LENGTH_LONG).show()
            return false
        }

        return true
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
                    exam.id = exam1ID!!
                    databaseExam.child(exam1ID).setValue(exam).addOnCompleteListener {
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
                    exam.id = exam2ID!!
                    databaseExam.child(exam2ID).setValue(exam).addOnCompleteListener {
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
                    exam.id = exam3ID!!
                    databaseExam.child(exam3ID).setValue(exam).addOnCompleteListener {
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
                    exam.id = exam4ID!!
                    databaseExam.child(exam4ID).setValue(exam).addOnCompleteListener {
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
                    exam.id = exam5ID!!
                    databaseExam.child(exam5ID).setValue(exam).addOnCompleteListener {
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
                    exam.id = exam6ID!!
                    databaseExam.child(exam6ID).setValue(exam).addOnCompleteListener {
                        Toast.makeText(applicationContext, "Exámen agregado", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        }

    }

}