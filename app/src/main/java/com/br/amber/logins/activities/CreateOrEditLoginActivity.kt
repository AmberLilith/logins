package com.br.amber.logins.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.br.amber.logins.R
import com.br.amber.logins.models.Login
import com.br.amber.logins.services.LoginService
import com.google.gson.Gson

class CreateOrEditLoginActivity : AppCompatActivity() {

    private lateinit var platformNameEditText: EditText
    private lateinit var userEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordRepeatEditText: EditText
    private lateinit var titleTextView: TextView
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private lateinit var receivedParameter: Parameters
    private val loginService = LoginService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_or_edit_login)

        platformNameEditText = findViewById(R.id.createOrEditLoginEditTextPlatformName)
        userEditText = findViewById(R.id.createOrEditLoginEditTextUser)
        passwordEditText = findViewById(R.id.createOrEditLoginEditTextPassword)
        passwordRepeatEditText = findViewById(R.id.createOrEditLoginEditTextRepeatPassword)
        titleTextView = findViewById(R.id.createOrEditLoginTitle)
        buttonSave = findViewById(R.id.createOrEditLoginButtonSave)
        buttonCancel = findViewById(R.id.createOrEditLoginButtonCancel)

        setActivityAccordingMethod()

        buttonSave.setOnClickListener {
            if (validateIfFieldsAreValids()) {
                val plataformName = platformNameEditText.text.trim().toString()
                val user = userEditText.text.trim().toString()
                val password = passwordEditText.text.trim().toString()
                if (receivedParameter.method == "create") {
                    loginService.createLogin(Login(plataformName, user, password), this)
                } else if (receivedParameter.method == "edit") {
                    loginService.editLogin(
                        Login(plataformName, user, password),
                        this,
                        receivedParameter.loginKey
                    )
                }
                returnToListLoginsActivity()
            }
        }

        buttonCancel.setOnClickListener {
            returnToListLoginsActivity()
        }
    }

    private fun returnToListLoginsActivity() {
        val intent = Intent(this, ListLoginsActivity::class.java)
        startActivity(intent)
    }


    @SuppressLint("SetTextI18n")
    fun setActivityAccordingMethod() {
        val gson = Gson()
        val parametersJson = intent.getStringExtra("parameters")
        receivedParameter = gson.fromJson(parametersJson, Parameters::class.java)
        if (receivedParameter.method == "edit") {
            titleTextView.text = "Editar dados do login"
            val loginKey = receivedParameter.loginKey
            loginService.getLoginForEdition(this, loginKey) { login ->
                if (login != null) {
                    platformNameEditText.text = Editable.Factory.getInstance()
                        .newEditable(login.plataformName)
                    userEditText.text =
                        Editable.Factory.getInstance().newEditable(login.user)
                    passwordEditText.text =
                        Editable.Factory.getInstance().newEditable(login.password)
                    passwordRepeatEditText.text =
                        Editable.Factory.getInstance().newEditable(login.password)
                }
            }
        } else {
            titleTextView.text = "Cadastrar um novo login"
        }
    }

    private fun validateIfFieldsAreValids(): Boolean {
        if (platformNameEditText.text.trim().isEmpty()) {
            platformNameEditText.error = "Nome da plataforma inválido!"
            return false
        } else if (userEditText.text.trim().isEmpty()) {
            userEditText.error = "Usuário inválido!"
            return false
        } else if (passwordEditText.text.trim().isEmpty()) {
            passwordEditText.error = "password inválido!"
            return false
        } else if (passwordEditText.text.trim().toString() != passwordRepeatEditText.text.trim()
                .toString()
        ) {
            passwordEditText.error = "As senhas não conferem!"
            passwordRepeatEditText.error = "As senhas não conferem!"
            return false
        }
        return true
    }

    data class Parameters(
        var method: String = "create",
        var loginKey: String
    )

}











