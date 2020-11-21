package com.example.bloqueoescolar

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_score.*
import kotlin.system.exitProcess

class Score : AppCompatActivity() {

    var idExam = ""
    var score = 0
    var reactives = 0
    var answersCorrects = ArrayList<Int>()
    lateinit var grade: String
    //var user: StructUser = StructUser("", "", "", "", "", "")
    var user = User("","","","", ArrayList())
    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    val database = FirebaseDatabase.getInstance().getReference("usuario").child(
        currentFirebaseUser!!.uid
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Se obtienen los datos para mostrar en pantalla
        idExam = intent.getStringExtra("idExam")!!
        score = intent.getIntExtra("score", 0)
        reactives = intent.getIntExtra("reactives", 0)
        answersCorrects = intent.getIntegerArrayListExtra("answersCorrects")!!
        grade = intent.getStringExtra("indexGrade")!!

        calculateScore()
    }

    override fun onStart() {
        super.onStart()

        // Presentar nuevamente
        test_again.setOnClickListener {
            val intent = Intent(this, Exams::class.java)
            intent.putExtra("grade", grade)
            startActivity(intent)
            // finish()
        }

        // Funcion para cerrar aplicación
        exit.setOnClickListener {
            finishAffinity();
            finishAndRemoveTask();
        }
    }

    fun calculateScore(){

        if(score/reactives >= .70){
            ImageResult.setImageResource(R.drawable.ic_validacion)

            if(exit.text == "Exit"){
                textScore.text = "Your score was $score out of $reactives possible"
                message.text = "¡CONGRATULATIONS, YOU ARE APPROVED"
            }else{
                textScore.text = "Tu puntuación fue $score de $reactives posibles"
                message.text = "¡FELICIDADES, APROBASTE!"
            }

            showUsername(true)
            //examApproved()

        }else{
            ImageResult.setImageResource(R.drawable.ic_triste)
            if(exit.text == "Exit"){
                textScore.text = "Your score was $score out of $reactives possible"
                message.text = "Don't worry, try it again"
            }else{
                textScore.text = "Tu puntuación fue $score de $reactives posibles"
                message.text = "No te preocupes, ¡intentalo nuevamente!"
            }

            showUsername(false)
        }

    }

    // Está función la usaré para meter el id del examen, al arreglo de ID's del usuario
    fun examApproved(){

        user.approved.add(idExam)
        //Toast.makeText(applicationContext,"${user.approved}", Toast.LENGTH_LONG).show()
        database.child("IDs").setValue(user.approved)
        /*var IDs = ArrayList<String>()
        IDs.add("1ero")
        IDs.add("2do")*/

        //database.child("IDs").setValue(IDs)

    }

    // Proximamente cambiaré esta función para cargar toda la info del usuario loggeado
    fun showUsername(approved: Boolean) {

        //Toast.makeText(this, "" + currentFirebaseUser!!.uid, Toast.LENGTH_LONG).show()

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Getting the string value of that node
                user.name = dataSnapshot.child("nombre").getValue(String::class.java)!!
                username.text = user.name

                if (approved) {
                    for (postSnapshot in dataSnapshot.child("IDs").children) {

                        var ID = postSnapshot.getValue(String::class.java)
                        //Toast.makeText(applicationContext,"$ID", Toast.LENGTH_LONG).show()
                        user.approved.add(ID!!)

                    }

                    //Toast.makeText(applicationContext, "${user.approved}", Toast.LENGTH_LONG).show()
                    examApproved()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }


        })


    }
}