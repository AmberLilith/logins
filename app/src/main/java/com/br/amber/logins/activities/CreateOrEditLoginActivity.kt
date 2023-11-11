package com.br.amber.logins.activities

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.br.amber.logins.dialogs.DialogGeneratePassword
import com.br.amber.logins.R
import com.br.amber.logins.models.Login
import com.br.amber.logins.services.LoginService
import com.google.gson.Gson

class CreateOrEditLoginActivity : AppCompatActivity() {

    private lateinit var platformNameEditText: EditText
    private lateinit var userEditText: EditText
    private lateinit var editTextpassword: EditText
    private lateinit var editTextRepeatpassword: EditText
    private lateinit var titleTextView: TextView
    private lateinit var buttonCallDialogPasswordOptions: Button
    private lateinit var buttonCopyPassword: Button
    private lateinit var buttonViewPassword: Button
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private lateinit var receivedParameter: Parameters
    private val loginService = LoginService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_or_edit_login)

        platformNameEditText = findViewById(R.id.createOrEditLoginEditTextPlatformName)
        userEditText = findViewById(R.id.createOrEditLoginEditTextUser)
        editTextpassword = findViewById(R.id.createOrEditLoginEditTextPassword)
        editTextRepeatpassword = findViewById(R.id.createOrEditLoginEditTextRepeatPassword)
        buttonCallDialogPasswordOptions = findViewById(R.id.createOrEditLoginButtonGeneratePassword)
        buttonCopyPassword = findViewById(R.id.createOrEditLoginButtonCopyPassword)
        buttonViewPassword = findViewById(R.id.createOrEditLoginButtonViewPassword)
        titleTextView = findViewById(R.id.createOrEditLoginTitle)
        buttonSave = findViewById(R.id.createOrEditLoginButtonSave)
        buttonCancel = findViewById(R.id.createOrEditLoginButtonCancel)

        setActivityAccordingMethod()

        buttonSave.setOnClickListener {
            if (validateIfFieldsAreValids()) {
                val plataformName = platformNameEditText.text.trim().toString()
                val user = userEditText.text.trim().toString()
                val password = editTextpassword.text.trim().toString()
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

        buttonCallDialogPasswordOptions.setOnClickListener{
            showDialogPasswordOPtions()
        }

        buttonCopyPassword.setOnClickListener{
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Texto Copiado", editTextpassword.text)
            clipboardManager.setPrimaryClip(clipData)
        }

        var invisiblePassword = true

        buttonViewPassword.setOnClickListener{
            if(invisiblePassword){
                editTextpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                editTextRepeatpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                buttonViewPassword.setBackgroundResource(R.drawable.baseline_visibility_off_24)
            }else{
                editTextpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                editTextRepeatpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                buttonViewPassword.setBackgroundResource(R.drawable.baseline_visibility_24)
            }
            invisiblePassword = !invisiblePassword
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
            loginService.getLoginForEdition(loginKey) { login ->
                if (login != null) {
                    platformNameEditText.text = Editable.Factory.getInstance()
                        .newEditable(login.plataformName)
                    userEditText.text =
                        Editable.Factory.getInstance().newEditable(login.user)
                    editTextpassword.text =
                        Editable.Factory.getInstance().newEditable(login.password)
                    editTextRepeatpassword.text =
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
        } else if (editTextpassword.text.trim().isEmpty()) {
            editTextpassword.error = "password inválido!"
            return false
        } else if (editTextpassword.text.trim().toString() != editTextRepeatpassword.text.trim()
                .toString()
        ) {
            editTextpassword.error = "As senhas não conferem!"
            editTextRepeatpassword.error = "As senhas não conferem!"
            return false
        }
        return true
    }

    fun showDialogPasswordOPtions() {
        val dialogGeneratePassword = DialogGeneratePassword()
        dialogGeneratePassword.show(supportFragmentManager, "DialogPassword")
        dialogGeneratePassword.showDialogPasswordOptions(editTextpassword, editTextRepeatpassword)

    }

    data class Parameters(
        var method: String = "create",
        var loginKey: String
    )

}











