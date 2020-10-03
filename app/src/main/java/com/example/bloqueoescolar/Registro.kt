package com.example.bloqueoescolar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.emergent_screen.*

class Registro : AppCompatActivity() {

    companion object {
        private const val TAG = "RegisterActivity"
    }

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.emergent_screen)
        auth = Firebase.auth

        btn_start.setOnClickListener(){
            val printName = name.text.toString()
            val printEmail =  email.text.toString().trim()
            val printPass =  password.text.toString().trim()
            val printConfPass = confirm_password.text.toString()

            this.createAccount(printEmail, printPass)
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount: $email")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                task ->
                Log.d(TAG, task.isSuccessful.toString())
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    this.sendEmailVerification()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Error al registrarse: ${task.exception}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext,
                        "Se ha enviado un correo de verificación a ${user.email} ",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(baseContext,
                        "Ocurrió un error al enviar el correo de verificación.",
                        Toast.LENGTH_SHORT).show()
                }
                // [END_EXCLUDE]
            }
        // [END send_email_verification]
    }
}