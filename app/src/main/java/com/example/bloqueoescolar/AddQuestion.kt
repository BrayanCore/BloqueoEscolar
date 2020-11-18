package com.example.bloqueoescolar

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

    var numberCorrect = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_question)

        database = Firebase.database.reference
        getDataGrade() //Se obtiene la informacion del grado.
    }

    override fun onStart() {
        super.onStart()

        // Se agrega una pregunta al examen
        add_question.setOnClickListener {
            addQuestion()
        }

        // Abre Dialogo para guardar examen
        send_exam_btn.setOnClickListener {
            terminarExamen()
        }
    }

    /**
     * Funcion que obtiene la informacion del grado, si ya tiene la informacion, no vuelve
     * a obtener la informacion
     */
    private fun getDataGrade() {
        if(grade.id.isNullOrEmpty()){
            grade = intent.getSerializableExtra("grade") as StructGrade
        }
    }

    /**
     * Funcion que hace las operaciones correspondientes para guardar
     * el examen.
     *
     */
    private fun terminarExamen() {
        // Agrega la pregunta si se lleno el examen
        if(validateFields()) {
            addQuestion()
        }

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

    /**
     * Funcion para agregar o preparar objeto de pregunta
     *
     */
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
                        else -> Toast.makeText(this, "Invalid Number",
                            Toast.LENGTH_LONG).show()
                    }

                    1 -> when(numbers[i]) {
                        1 -> {
                            answers.optionTwo = answer1.text.toString()
                            numberCorrect = i+1
                        }
                        2 -> answers.optionTwo =  answer2.text.toString()
                        3 -> answers.optionTwo = answer3.text.toString()
                        4 -> answers.optionTwo =  answer4.text.toString()
                        else -> Toast.makeText(this, "Invalid Number",
                            Toast.LENGTH_LONG).show()
                    }

                    2 -> when(numbers[i]){
                        1 -> {
                            answers.optionThree = answer1.text.toString()
                            numberCorrect = i+1
                        }
                        2 -> answers.optionThree =  answer2.text.toString()
                        3 -> answers.optionThree = answer3.text.toString()
                        4 -> answers.optionThree =  answer4.text.toString()
                        else -> Toast.makeText(this, "Invalid Number",
                            Toast.LENGTH_LONG).show()
                    }

                    3 -> when(numbers[i]) {
                        1 -> {
                            answers.optionFour = answer1.text.toString()
                            numberCorrect = i+1
                        }
                        2 -> answers.optionFour =  answer2.text.toString()
                        3 -> answers.optionFour = answer3.text.toString()
                        4 -> answers.optionFour =  answer4.text.toString()
                        else -> Toast.makeText(this, "Invalid Number",
                            Toast.LENGTH_LONG).show()
                    }

                    else -> Toast.makeText(this, "Invalid Number",
                        Toast.LENGTH_LONG).show()

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
        } else {
            Toast.makeText(applicationContext, "COMPLETA TODOS LOS CAMPOS",
                Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Se agrega la pregunta al examen
     */
    fun addQuestionToExam(questionAdded: StructQuestion){
        exam.questions.add(questionAdded)
        clearFields()
    }

    /**
     * Guarda el examen en la base de datos.
     */
    fun sendExam() {
        // Se establece referencia a tabla de examenes del grado actual
        database = FirebaseDatabase.getInstance().getReference("Grados/${grade.id}/subjects")

        // Se crea el id del examen
        val examID = database.push().key
        exam.id = examID!!

        // Se guarda el examen
        database.child(examID).setValue(exam).addOnCompleteListener {
            finish()
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
            return false
        }

        return true
    }
}