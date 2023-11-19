package com.br.amber.logins.activities

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.br.amber.logins.dialogs.DialogGeneratePassword
import com.br.amber.logins.R
import com.br.amber.logins.models.Login
import com.br.amber.logins.services.LoginService
import com.br.amber.logins.services.UserService
import com.br.amber.logins.utils.Crypt
import com.google.gson.Gson

class CreateOrEditLoginActivity : AppCompatActivity() {

    private lateinit var platformNameEditText: EditText
    private lateinit var userNameEditText: EditText
    private lateinit var editTextpassword: EditText
    private lateinit var editTextRepeatpassword: EditText
    private lateinit var titleTextView: TextView
    private lateinit var buttonCallDialogPasswordOptions: Button
    private lateinit var buttonCopyPassword: Button
    private lateinit var buttonShowPassword: Button
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private lateinit var buttonCopyUserName: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var receivedParameter: Parameters
    private val loginService = LoginService()
    private val userService = UserService()
    private lateinit var secretKey: String
    private lateinit var crypt: Crypt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_or_edit_login)

        platformNameEditText = findViewById(R.id.createOrEditLoginEditTextPlatformName)
        userNameEditText = findViewById(R.id.createOrEditLoginEditTextUser)
        editTextpassword = findViewById(R.id.createOrEditLoginEditTextPassword)
        editTextRepeatpassword = findViewById(R.id.createOrEditLoginEditTextRepeatPassword)
        buttonCallDialogPasswordOptions = findViewById(R.id.createOrEditLoginButtonGeneratePassword)
        buttonCopyPassword = findViewById(R.id.createOrEditLoginButtonCopyPassword)
        buttonShowPassword = findViewById(R.id.createOrEditLoginButtonShowPassword)
        buttonCopyUserName = findViewById(R.id.createOrEditLoginButtonCopyUserName)
        titleTextView = findViewById(R.id.createOrEditLoginTitle)
        buttonSave = findViewById(R.id.createOrEditLoginButtonSave)
        buttonCancel = findViewById(R.id.createOrEditLoginButtonCancel)
        progressBar = findViewById(R.id.createOrEditLoginProgressBar)
        crypt = Crypt()

        userService.getSecretKey { retrievedSecretKey ->
             secretKey = retrievedSecretKey!!
            setActivityAccordingMethod()
        }



        buttonSave.setOnClickListener {
            if (validateIfFieldsAreValids()) {
                progressBar.visibility = View.VISIBLE
                val plataformName = platformNameEditText.text.trim().toString()
                val user = userNameEditText.text.trim().toString()
                val password = editTextpassword.text.trim().toString()
                userService.getUser { datas ->
                    if(datas != null){
                       val criptedPassword = crypt.encrypt(password, datas.secretKey, datas.aggregator)
                        if (receivedParameter.method == "create") {
                            loginService.createLogin(Login("",plataformName, user, criptedPassword), this)
                        } else if (receivedParameter.method == "edit") {
                            loginService.editLogin(
                                Login("",plataformName, user, criptedPassword),
                                this,
                                receivedParameter.loginKey
                            )
                        }
                    }
                    progressBar.visibility = View.GONE
                    returnToListLoginsActivity()
                }


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
            val clipData = ClipData.newPlainText("Senha copiada", editTextpassword.text)
            clipboardManager.setPrimaryClip(clipData)
        }

        buttonCopyUserName.setOnClickListener{
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Nome usuário Copiado", userNameEditText.text)
            clipboardManager.setPrimaryClip(clipData)
        }

        var invisiblePassword = true

        buttonShowPassword.setOnClickListener{
            if(invisiblePassword){
                editTextpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                editTextRepeatpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                buttonShowPassword.setBackgroundResource(R.drawable.baseline_visibility_off_24)
            }else{
                editTextpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                editTextRepeatpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                buttonShowPassword.setBackgroundResource(R.drawable.baseline_visibility_24)
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
            progressBar.visibility = View.VISIBLE
            loginService.getLoginForEdition(loginKey) { login ->
                if (login != null) {
                    userService.getUser { datas ->
                        if(datas != null){
                           val decryptedPassword = crypt.decrypt(login.password, datas.secretKey, datas.aggregator)
                            platformNameEditText.text = Editable.Factory.getInstance()
                                .newEditable(login.plataformName)
                            userNameEditText.text =
                                Editable.Factory.getInstance().newEditable(login.user)
                            editTextpassword.text =
                                Editable.Factory.getInstance().newEditable(decryptedPassword)
                            editTextRepeatpassword.text =
                                Editable.Factory.getInstance().newEditable(decryptedPassword)
                        }
                        progressBar.visibility = View.GONE
                    }

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
        } else if (userNameEditText.text.trim().isEmpty()) {
            userNameEditText.error = "Usuário inválido!"
            return false
        } else if (editTextpassword.text.trim().isEmpty()) {
            editTextpassword.error = "password inválido!"
            return false
        } else if (editTextpassword.text.trim().contains(Regex("[¬\\\\\"'\$]"))) {
            editTextpassword.error = "password não pode conter: (¬ \\ \" ' \$)"
            return false
        }else if (editTextpassword.text.trim().toString() != editTextRepeatpassword.text.trim()
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











