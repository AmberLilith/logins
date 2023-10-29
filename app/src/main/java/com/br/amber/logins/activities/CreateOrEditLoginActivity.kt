package com.br.amber.logins.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.br.amber.logins.R
import com.br.amber.logins.models.Login
import com.br.amber.logins.utils.GeneralUse
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class CreateOrEditLoginActivity : AppCompatActivity() {

    private lateinit var platformNameEditText: EditText
    private lateinit var userEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var titleTextView: TextView
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private lateinit var transactionMessage: String
    private lateinit var receivedParameter: Parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_or_edit_login)
        val database = Firebase.database
        val loggedUserLoginsReference =
            database.getReference("amber_silva_gmail_com") //TODO pegar usuário logado no futuro

        platformNameEditText = findViewById(R.id.platformName)
        userEditText = findViewById(R.id.user)
        passwordEditText = findViewById(R.id.password)
        titleTextView = findViewById(R.id.activityCreateOrEditLoginTitle)
        buttonSave = findViewById(R.id.buttonSave)
        buttonCancel = findViewById(R.id.activityCreateOrEditLoginButtonCancel)

        setActivityAccordingMethod(loggedUserLoginsReference)

        buttonSave.setOnClickListener {
            if (validateIfFieldsAreValids()) {
                val plataformName = platformNameEditText.text.trim().toString()
                val user = userEditText.text.trim().toString()
                val password = passwordEditText.text.trim().toString()
                if(receivedParameter.method == "create"){
                    try {
                        val novaChave = loggedUserLoginsReference.push().key
                        novaChave?.let {
                            loggedUserLoginsReference.child(it)
                                .setValue(Login(plataformName, user, password))
                        }
                    } catch (e: Exception) {
                        transactionMessage = "Ocorreu um erro. Transação não concluída!"
                        println("Login não cadastrado. Erro: ${e.message}")
                    }
                } else if(receivedParameter.method == "edit"){
                    loggedUserLoginsReference.child(receivedParameter.loginKey).setValue(Login(plataformName, user, password))
                }
                val intent = Intent(this, ListLoginsActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, transactionMessage, Toast.LENGTH_LONG).show()
            }
        }

        buttonCancel.setOnClickListener {
            val intent = Intent(this, ListLoginsActivity::class.java)
            startActivity(intent)
        }
    }


    fun setActivityAccordingMethod(loggedUserLoginsReference: DatabaseReference) {
        val gson = Gson()
        val parametersJson = intent.getStringExtra("parameters")
        receivedParameter = gson.fromJson(parametersJson, Parameters::class.java)
        if (receivedParameter.method == "edit") {
            titleTextView.text = "Editar dados do login"
            transactionMessage = "Login alterado com sucesso!"
            val node = receivedParameter.loginKey
            loggedUserLoginsReference.child(node)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val login = dataSnapshot.getValue(Login::class.java)
                            if (login != null) {
                                platformNameEditText.text = Editable.Factory.getInstance()
                                    .newEditable(login.plataformName)
                                userEditText.text =
                                    Editable.Factory.getInstance().newEditable(login.user)
                                passwordEditText.text =
                                    Editable.Factory.getInstance().newEditable(login.password)
                            }
                        } else {
                            println("O nó $node não existe")
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        println("Erro ao recuperar dados: ${databaseError.message}")
                    }
                })
        } else {
            titleTextView.text = "Cadastrar um novo login"
            transactionMessage = "Login cadastrado com sucesso!"
        }
    }

    fun validateIfFieldsAreValids(): Boolean {
        if (platformNameEditText.text.trim().isEmpty()) {
            platformNameEditText.error = "Nome da plataforma inválido!"
            return false
        } else if (platformNameEditText.text.trim()
                .contains(Regex("[!@#\$%&*()\\-+=\\[{}\\]>;?|,]"))
        ) {
            platformNameEditText.error =
                "Nome da plataforma só pode conter letras, números e espaços!"
        } else if (userEditText.text.trim().isEmpty()) {
            userEditText.error = "Usuário inválido!"
            return false
        } else if (passwordEditText.text.trim().isEmpty()) {
            passwordEditText.error = "password inválido!"
            return false
        }
        return true
    }

    data class Parameters(
        var method: String = "create",
        var loginKey: String
    )

}











