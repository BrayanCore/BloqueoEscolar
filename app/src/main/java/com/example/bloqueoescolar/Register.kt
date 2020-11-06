package com.example.bloqueoescolar

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bloqueoescolar.domain.struct.StructNewUser
import com.example.bloqueoescolar.domain.struct.StructUser
import com.example.bloqueoescolar.service.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.acitivy_register.*

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userService: UserService


    companion object {
        private const val TAG = "RegistroActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivy_register)
        auth = Firebase.auth
        userService = UserService()

        btn_start.setOnClickListener(){
            val name = name.text.toString()
            val email =  email.text.toString().trim()
            val pass =  password.text.toString().trim()
            val confPass = confirm_password.text.toString()
            val registro = StructNewUser(name, email, pass, confPass)
            createAccount(registro)
        }
    }

    private fun createAccount(registro: StructNewUser) {

        if(!formularioValido(registro)) { return }

        Log.d(TAG, "crearCuenta: ${registro.email}")

        auth.createUserWithEmailAndPassword(registro.email, registro.password)
            .addOnCompleteListener(this) { task ->
                Log.d(TAG, task.isSuccessful.toString())
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    this.enviarCorreoVerificacion(registro)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    showToast("Error al crear al usuario.")
                }
            }
    }

    private fun enviarCorreoVerificacion(registro: StructNewUser) {
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    this.registrarUsuario(registro, user)
                    showToast("Se ha enviado un correo de verificación a ${user.email} ")
                    val intent = Intent(this@Register, Login::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    showToast("Ocurrió un error al enviar el correo de verificación.")
                }
            }
    }

    private fun registrarUsuario(registro:StructNewUser, user: FirebaseUser) {
        userService = UserService()
        val usuario = StructUser(user.uid,
            registro.nombre, "test", "test", registro.email, registro.password)
        userService.registerUsuario(usuario)
    }

    private fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun formularioValido(registro: StructNewUser): Boolean {
        var valid = true;

        val nombreRev = registro.nombre
        if (TextUtils.isEmpty(nombreRev)) {
            name.error = "Ingrese un nombre."
            valid = false
        }

        val emailRev = registro.email
        if (TextUtils.isEmpty(emailRev)) {
            email.error = "Ingrese un correo eléctronico."
            valid = false
        }

        val passRev = registro.password
        if (TextUtils.isEmpty(passRev)) {
            password.error = "Ingrese una contraseña."
            valid = false
        }

        val confPassRev = registro.confirmPassword
        if (TextUtils.isEmpty(confPassRev)) {
            confirm_password.error = "Debe confirmar la contraseña."
            valid = false
        }

        if (!passRev.equals(confPassRev)) {
            Toast.makeText(baseContext, "Las contraseñas no coinciden.",
                Toast.LENGTH_SHORT).show()
            valid = false
        }

        return valid
    }
}