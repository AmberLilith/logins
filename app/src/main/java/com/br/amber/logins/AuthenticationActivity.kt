package com.br.amber.logins

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.amber.logins.activities.ListLoginsActivity
import com.br.amber.logins.activities.RegisterEmailAndPasswordActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonAuth: Button
    private lateinit var textViewRegister: TextView
    private lateinit var textViewResetPassword: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authentication)
        buttonAuth = findViewById(R.id.authenticationButtonAuthenticate)
        editTextEmail = findViewById(R.id.authenticationEditTextEmail)
        editTextPassword = findViewById(R.id.authenticationEditTextPassword)
        textViewRegister = findViewById(R.id.authenticationTextViewRegister)
        textViewResetPassword = findViewById(R.id.authenticationTextViewForgottenPassword)
        auth = Firebase.auth

        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterEmailAndPasswordActivity::class.java)
            startActivity(intent)
        }

        textViewResetPassword.setOnClickListener {
            val modalResetPassword = DialogResetPassoword()
            modalResetPassword.show(supportFragmentManager, "DialogResetPassword")
        }



        buttonAuth.setOnClickListener {
            auth.signInWithEmailAndPassword(
                editTextEmail.text.toString(),
                editTextPassword.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    /*public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        } else {
            val intent = Intent(this, RegisterEmailAndPasswordActivity::class.java)
            startActivity(intent)
        }
    }*/

    private fun reload() {
        val user = auth.currentUser
        user?.reload()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User data has been reloaded successfully
                } else {
                    // Handle the error
                    // task.exception can provide more details about the error
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val welcomeMessage = "Bem-vindo, ${user.email}!"
            Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ListLoginsActivity::class.java)
            startActivity(intent)
        } else {
            val errorMessage = "Falha no registro."
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}