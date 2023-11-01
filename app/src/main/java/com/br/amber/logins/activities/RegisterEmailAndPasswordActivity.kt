package com.br.amber.logins.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.amber.logins.R
import com.br.amber.logins.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterEmailAndPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextUserName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextRepeatPassword: EditText
    private lateinit var buttonRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_email_and_password)

        editTextEmail = findViewById(R.id.registerEmailPasswordEditTextEmail)
        editTextPassword = findViewById(R.id.createOrEditLoginEditTextPassword)
        editTextRepeatPassword = findViewById(R.id.createOrEditLoginEditTextRepeatPassword)
        editTextUserName = findViewById(R.id.registerEmailPasswordTextTextUserName)
        buttonRegister = findViewById(R.id.registerEmailPasswordButtonRegister)
        auth = Firebase.auth

        buttonRegister.setOnClickListener {
            if (validateIfFieldsAreValids()) {
                auth.createUserWithEmailAndPassword(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            val userId = user!!.uid
                            val userName = editTextUserName.text.toString()
                            val database = Firebase.database
                            val data = mutableMapOf<String, Any>()
                            data["datas"] = User(userName, "")
                            data["logins"] = ""
                            database.reference.child(userId).setValue(data)
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
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
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // O usuário está logado, você pode atualizar a UI de acordo.
            // Por exemplo, você pode redirecioná-los para outra atividade ou exibir uma mensagem de boas-vindas.
            val welcomeMessage = "Bem-vindo, ${user.email}!"
            Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ListLoginsActivity::class.java)
            startActivity(intent)

            // Você pode redirecionar o usuário para outra atividade
            // val intent = Intent(this, DashboardActivity::class.java)
            // startActivity(intent)

            // Ou você pode realizar outras ações, como atualizar elementos da interface do usuário.
            // Exemplo: Atualizar um TextView
            // val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
            // welcomeTextView.text = welcomeMessage
        } else {
            // O registro falhou, você pode lidar com isso aqui.
            // Por exemplo, exiba uma mensagem de erro na interface do usuário.
            // Ou limpe os campos de entrada de email e senha.
            val errorMessage = "Falha no registro."
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()

            // Exemplo: Limpar os campos de entrada
            // editTextEmail.text.clear()
            // editTextPassword.text.clear()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

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

    fun validateIfFieldsAreValids(): Boolean {
        if (editTextEmail.text.trim().isEmpty()) {
            editTextEmail.error = "Email vazio!"
            return false
        } else if (!isEmailValid(editTextEmail.text.trim().toString())) {
            editTextEmail.error = "Não é um email válido!"
            return false
        } else if (editTextPassword.text.trim()
                .isEmpty() || editTextPassword.text.trim().length < 6
        ) {
            editTextPassword.error = "password inválido!"
            return false
        } else if (editTextPassword.text.trim() != editTextRepeatPassword.text.trim()) {
            editTextPassword.error = "As senhas não conferem!"
            editTextRepeatPassword.error = "As senhas não conferem!"
            return false
        }
        return true
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }
}