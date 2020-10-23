package com.example.bloqueoescolar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        // Boton inicio de sesion
        btn_login.setOnClickListener(){
            val email =  txt_input_email.text.toString().trim()
            val pass =  txt_input_pass.text.toString().trim()
            inicioSesion(email, pass)
        }

        // Boton crear cuenta
        txtLinkRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun inicioSesion(email:String, password: String){
        Log.d(TAG, "Inicio sesion: $email")
        // Validacion de formulario
        if(!formularioValido(email, password)) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful) {
                    Log.d(TAG, "inicio de sesion exitoso")
                    val user = auth.currentUser
                    Log.d(TAG, "Verificado: ${user?.isEmailVerified}")
                    user?.let { validarCorreoVerificado(user.isEmailVerified) }
                } else {
                    Log.w(TAG, "Error al iniciar sesion", task.exception)
                    Toast.makeText(baseContext, "Credenciales incorrectas",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun validarCorreoVerificado(emailVerificado: Boolean) {
        if(!emailVerificado) {
            Toast.makeText(baseContext, "Debe verificar su correo primero",
                Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this@LoginActivity, SelectGrade::class.java)
            startActivity(intent)
        }
    }

    private fun formularioValido(emailRev:String, passRev: String): Boolean {
        var valid = true;

        if (TextUtils.isEmpty(emailRev)) {
            txt_input_email.error = "Ingrese un correo eléctronico."
            valid = false
        }

        if (TextUtils.isEmpty(passRev)) {
            txt_input_pass.error = "Ingrese una contraseña."
            valid = false
        }

        return valid
    }

    private fun signOut() {
        auth.signOut()
    }
}