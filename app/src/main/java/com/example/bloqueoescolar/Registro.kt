package com.example.bloqueoescolar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
//import kotlinx.android.synthetic.main.emergent_screen.*


class Registro : AppCompatActivity() {

    var usuario = User("", "", "", "", arrayListOf<String>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.emergent_screen)

        /*btn_submit.setOnClickListener(){
            sendData();
        }*/

    }

    /*private fun sendData(){

        if(name.text.isNullOrEmpty() == true || email.text.isNullOrEmpty() == true || password.text.isNullOrEmpty() == true || confirm_password.text.isNullOrEmpty() == true){
            Toast.makeText(
                this,
                "COMPLETA TODOS LOS CAMPOS", Toast.LENGTH_LONG
            ).show()
            return
        }

        if(password.text.toString() == confirm_password.text.toString()){
            usuario.name = name.text.toString();
            usuario.email = email.text.toString();
            usuario.password = password.text.toString()

            // Write a message to the database

            // Write a message to the database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference()

            myRef.setValue("Hello, World!")

            /*val database = FirebaseDatabase.getInstance().getReference("Exámenes")
            val UserID = database.push().key

            val user = User(UserID!!, usuario.name, usuario.email, usuario.password, ArrayList<Int>())

            /*database.child(UserID).setValue(user).addOnCompleteListener{
                Toast.makeText(applicationContext, "User save successfully", Toast.LENGTH_LONG).show()
            }*/
            database.child("users").child(UserID).setValue(user)*/

        }else{
            Toast.makeText(
                this,
                "Las contraseñas no coinciden", Toast.LENGTH_LONG
            ).show()
            return
        }

        val intent = Intent(this, SelectGrade::class.java)
        startActivity(intent)
        finish()

    }*/

}