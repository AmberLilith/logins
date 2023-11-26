package com.br.amber.logins.activities

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.br.amber.logins.R
import com.br.amber.logins.dialogs.DialogGeneratePassword
import com.br.amber.logins.services.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextUserName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextRepeatPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var buttonCancel: Button
    private lateinit var buttonGeneratePassword: Button
    private lateinit var buttonViewPassword: Button
    private lateinit var buttonCopyPassword: Button
    private lateinit var buttonUploadPicture: Button
    private lateinit var imageViewPicture: ImageView
    private lateinit var progressBar: ProgressBar
    private var imageUri: Uri? = null
    private var loggedUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        editTextEmail = findViewById(R.id.registerUserEditTextEmail)
        editTextPassword = findViewById(R.id.registerEmailPasswordEditTextPassword)
        editTextRepeatPassword = findViewById(R.id.registerEmailPasswordEditTextRepeatPassword)
        editTextUserName = findViewById(R.id.registerEmailPasswordTextTextUserName)
        buttonRegister = findViewById(R.id.registerEmailPasswordButtonRegister)
        buttonCancel = findViewById(R.id.registerEmailPasswordButtonCancel)
        buttonGeneratePassword = findViewById(R.id.registerEmailPasswordButtonGeneratePassword)
        buttonViewPassword = findViewById(R.id.registerEmailPasswordButtonViewPassword)
        buttonCopyPassword = findViewById(R.id.registerEmailPasswordButtonCopyPassword)
        buttonUploadPicture = findViewById(R.id.registerUserButtonUploadPicture)
        imageViewPicture = findViewById(R.id.registerUserImageViewPicture)
        progressBar = findViewById(R.id.registerUserProgressBar)
        auth = Firebase.auth


        buttonRegister.setOnClickListener {
            val userService = UserService(this, progressBar)
            if (validateIfFieldsAreValids()) {
                userService.saveEmailAndPassword(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString(),
                    editTextUserName.text.toString(),
                    imageUri
                )
            }
        }

        buttonCancel.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }

        buttonGeneratePassword.setOnClickListener {
            showDialogPasswordOPtions()
        }

        var invisiblePassword = true
        buttonViewPassword.setOnClickListener {
            if (invisiblePassword) {
                editTextPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                editTextRepeatPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                buttonViewPassword.setBackgroundResource(R.drawable.baseline_visibility_off_24)
            } else {
                editTextPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                editTextRepeatPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                buttonViewPassword.setBackgroundResource(R.drawable.baseline_visibility_24)
            }
            invisiblePassword = !invisiblePassword
        }

        buttonCopyPassword.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Texto Copiado", editTextPassword.text)
            clipboardManager.setPrimaryClip(clipData)
        }

        buttonUploadPicture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            getContent.launch(intent)
        }

        imageViewPicture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            getContent.launch(intent)
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUri = result.data?.data
                Picasso.get().load(imageUri).into(imageViewPicture)
                buttonUploadPicture.visibility = View.GONE
                imageViewPicture.visibility = View.VISIBLE
            }
        }


    public override fun onStart() {
        super.onStart()
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
                    Log.i(javaClass.simpleName, "Usuário recarregado!")
                } else {
                    Log.e(javaClass.simpleName, task.exception?.message.toString())
                }
            }
    }

    private fun validateIfFieldsAreValids(): Boolean {
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
        } else if (editTextPassword.text.trim().toString() != editTextRepeatPassword.text.trim()
                .toString()
        ) {
            editTextPassword.error = "As senhas não conferem!"
            editTextRepeatPassword.error = "As senhas não conferem!"
            return false
        }
        return true
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    private fun showDialogPasswordOPtions() {
        val dialogGeneratePassword = DialogGeneratePassword()
        dialogGeneratePassword.show(supportFragmentManager, "DialogPassword")
        dialogGeneratePassword.showDialogPasswordOptions(editTextPassword, editTextRepeatPassword)

    }
}




