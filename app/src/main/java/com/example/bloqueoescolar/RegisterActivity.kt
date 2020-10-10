package com.example.bloqueoescolar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.bloqueoescolar.domain.entity.RegistroUsuario
import com.example.bloqueoescolar.domain.entity.Usuario
import com.example.bloqueoescolar.service.UsuarioService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.acitivy_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var usuarioService: UsuarioService


    companion object {
        private const val TAG = "RegistroActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivy_register)
        auth = Firebase.auth
        usuarioService = UsuarioService()

        btn_start.setOnClickListener(){
            val name = name.text.toString()
            val email =  email.text.toString().trim()
            val pass =  password.text.toString().trim()
            val confPass = confirm_password.text.toString()
            val registro = RegistroUsuario(name, email, pass, confPass)
            createAccount(registro)
        }
    }

    private fun createAccount(registro: RegistroUsuario) {

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

    private fun enviarCorreoVerificacion(registro: RegistroUsuario) {
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    this.registrarUsuario(registro, user)
                    showToast("Se ha enviado un correo de verificación a ${user.email} ")
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    showToast("Ocurrió un error al enviar el correo de verificación.")
                }
            }
    }

    private fun registrarUsuario(registro:RegistroUsuario, user: FirebaseUser) {
        usuarioService = UsuarioService()
        val usuario = Usuario(user.uid,
            registro.nombre, "test", "test", registro.email, registro.password)
        usuarioService.registerUsuario(user.uid, usuario)
    }

    private fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun formularioValido(registro: RegistroUsuario): Boolean {
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