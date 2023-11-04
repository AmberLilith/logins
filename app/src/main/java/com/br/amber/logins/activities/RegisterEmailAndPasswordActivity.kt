package com.br.amber.logins.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.amber.logins.AuthenticationActivity
import com.br.amber.logins.DialogPasswordOptions
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
    private lateinit var buttonCancel: Button
    private lateinit var buttonGeneratePassword: Button
    private lateinit var buttonViewPassword: Button
    private lateinit var buttonCopyPassword: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_email_and_password)

        editTextEmail = findViewById(R.id.registerEmailPasswordEditTextEmail)
        editTextPassword = findViewById(R.id.registerEmailPasswordEditTextPassword)
        editTextRepeatPassword = findViewById(R.id.registerEmailPasswordEditTextRepeatPassword)
        editTextUserName = findViewById(R.id.registerEmailPasswordTextTextUserName)
        buttonRegister = findViewById(R.id.registerEmailPasswordButtonRegister)
        buttonCancel = findViewById(R.id.registerEmailPasswordButtonCancel)
        buttonGeneratePassword = findViewById(R.id.registerEmailPasswordButtonGeneratePassword)
        buttonViewPassword = findViewById(R.id.registerEmailPasswordButtonViewPassword)
        buttonCopyPassword = findViewById(R.id.registerEmailPasswordButtonCopyPassword)
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

        buttonCancel.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }

        buttonGeneratePassword.setOnClickListener {
            showDialogPasswordOPtions()
        }

        var invisiblePassword = true
        buttonViewPassword.setOnClickListener {
            if(invisiblePassword){
                editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                editTextRepeatPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                buttonViewPassword.setBackgroundResource(R.drawable.baseline_visibility_off_24)
            }else{
                editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                editTextRepeatPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                buttonViewPassword.setBackgroundResource(R.drawable.baseline_visibility_24)
            }
            invisiblePassword = !invisiblePassword
        }

        buttonCopyPassword.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Texto Copiado", editTextPassword.text)
            clipboardManager.setPrimaryClip(clipData)
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
        } else if (editTextPassword.text.trim().toString() != editTextRepeatPassword.text.trim().toString()) {
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

    fun showDialogPasswordOPtions() {
        val dialogPasswordOptions = DialogPasswordOptions()
        dialogPasswordOptions.show(supportFragmentManager, "DialogPassword")
        dialogPasswordOptions.showDialogPasswordOptions(editTextPassword, editTextRepeatPassword)

    }
}



/*


Aqui está um exemplo de como obter a URL de um arquivo no Cloud Storage:

Kotlin
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

fun main(args: Array<String>) {

    // Cria uma instância do FirebaseStorage
    val storage = FirebaseStorage.getInstance()

    // Cria uma referência para o bucket de armazenamento
    val bucket = storage.bucket("my-bucket")

    // Cria uma referência para o arquivo
    val fileReference = bucket.file("my-file.jpg")

    // Obtém a URL do arquivo
    val downloadUrl = fileReference.getDownloadUrl()

    // Insere a URL no ImageView
    imageView.setImageURI(downloadUrl)
}



}*/
