package com.br.amber.logins.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.amber.logins.dialogs.DialogGeneratePassword
import com.br.amber.logins.R
import com.br.amber.logins.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
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
        auth = Firebase.auth

        buttonRegister.setOnClickListener {
            if (validateIfFieldsAreValids()) {
                auth.createUserWithEmailAndPassword(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "createUserWithEmail:success")
                            loggedUser = auth.currentUser!!
                            val userId = loggedUser!!.uid
                            val userName = editTextUserName.text.toString()
                            val database = Firebase.database
                            val data = mutableMapOf<String, Any>()
                            data["datas"] = User(userName, "")
                            data["logins"] = ""
                            database.reference.child(userId).setValue(data)
                            salvePictureInCloudStorage()
                            updateUI(loggedUser)
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

        buttonUploadPicture.setOnClickListener {
            // Abre a galeria de imagens
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
        }

        imageViewPicture.setOnClickListener {
            // Abre a galeria de imagens
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
        }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageUri = data?.data ?: return
            Picasso.get().load(imageUri).into(imageViewPicture)
            buttonUploadPicture.visibility = View.GONE
            imageViewPicture.visibility = View.VISIBLE
        }
    }

    private fun salvePictureInCloudStorage() {
        if(loggedUser != null){
            if(imageUri != null){
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                val currentUserId = loggedUser!!.uid
                val imageName = "$currentUserId.jpg"
                val imageRef = storageRef.child("images").child(currentUserId).child(imageName)
                imageRef.putFile(imageUri!!)
                    .addOnSuccessListener {
                        Log.d(this.javaClass.simpleName, "Imagem salva no Cloud Storage")
                    }
                    .addOnFailureListener {
                        Log.d(this.javaClass.simpleName, "Imagem não salva no Cloud Storage. Erro: ${it.message}")
                    }
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




