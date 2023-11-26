package com.br.amber.logins.services

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.br.amber.logins.activities.ListLoginsActivity
import com.br.amber.logins.models.Login
import com.br.amber.logins.models.User
import com.br.amber.logins.utils.GeneralUse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlin.random.Random

class UserService(private val contextFromActivityWhoCalled: Context, private val progressBar: ProgressBar) {

    /*private val firebaseAuth = FirebaseAuth.getInstance()
    private val currentUser = firebaseAuth.currentUser
    private val userId = currentUser!!.uid
    private val database = FirebaseDatabase.getInstance()
    private val userNodeReference = database.getReference(userId).child("user")*/

    fun getUser(callback: (User?) -> Unit) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userId = currentUser!!.uid
        val database = FirebaseDatabase.getInstance()
        val userNodeReference = database.getReference(userId).child("user")
        try {
            userNodeReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val user = dataSnapshot.getValue(User::class.java)
                        callback(user)
                    } else {
                        Log.d(this.javaClass.simpleName, "Dados não existem")
                        callback(null)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(
                        this.javaClass.simpleName,
                        "Erro ao recuperar dados: ${databaseError.message}"
                    )
                    callback(null)
                }
            })
        } catch (e: Exception) {
            Log.e(
                this.javaClass.simpleName,
                "Não foi possível recuperar o login! Erro: ${e.message}"
            )
        }
    }

    fun getSecretKey(callback: (String?) -> Unit) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userId = currentUser!!.uid
        val database = FirebaseDatabase.getInstance()
        val userNodeReference = database.getReference(userId).child("user")
        try {
            userNodeReference.child("secretKey")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val secretKey = dataSnapshot.getValue(String::class.java)
                            callback(secretKey)
                        } else {
                            Log.d(this.javaClass.simpleName, "Registro não existe")
                            callback(null)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(
                            this.javaClass.simpleName,
                            "Erro ao recuperar dados: ${databaseError.message}"
                        )
                        callback(null)
                    }
                })
        } catch (e: Exception) {
            Log.e(
                this.javaClass.simpleName,
                "Não foi possível recuperar o login! Erro: ${e.message}"
            )
        }
    }

    fun getFistName(wholeName: String): String {
        val names = wholeName.split(" ")
        return names[0]
    }

    fun saveEmailAndPassword(
        email: String,
        password: String,
        userName: String,
        imageUri: Uri?
    ) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Log.d(TAG, "createUserWithEmail:success")
            saveDataInRealtimeDatabase(userName, imageUri)
        }
            .addOnFailureListener {
                Log.d(
                    this.javaClass.simpleName,
                    "Email e senha não salvos! Erro: ${it.message}"
                )
            }
    }

    private fun saveDataInRealtimeDatabase(
        userName: String,
        imageUri: Uri?
    ){
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser!!.uid

        val database = Firebase.database
        val data = mutableMapOf<String, Any>()
        data["user"] = User(userName, GeneralUse.getRandomHash(), Random.nextInt(10))
        data["logins"] = mutableMapOf(
            Pair(
                "doNotDelete",
                Login(
                    "Não exlcuir esse registro!!!",
                    "Não exlcuir esse registro!!!",
                    "Não exlcuir esse registro!!!",
                    "Não exlcuir esse registro!!!"
                )
            )
        )

        database.reference.child(userId).setValue(data)
            .addOnSuccessListener {
                Log.d(this.javaClass.simpleName, "Dados salvos com sucesso no Realtime Database!")
                savePictureInCloudStorage(imageUri)
            }
            .addOnFailureListener {
                Log.d(
                    this.javaClass.simpleName,
                    "Dados não salvos no Realtime Database. Erro: ${it.message}"
                )
            }
    }

    private fun savePictureInCloudStorage(imageUri: Uri?) {
        val auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser!!.uid
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageName = "$currentUserId.jpg"
        val imageRef = storageRef.child("images").child(currentUserId).child(imageName)

        if (imageUri != null) {
            imageRef.putFile(imageUri)
                .addOnSuccessListener {
                    Log.d(this.javaClass.simpleName, "Imagem salva no Cloud Storage")
                    updateUI(this.progressBar)
                }
                .addOnFailureListener {
                    Log.d(
                        this.javaClass.simpleName,
                        "Imagem não salva no Cloud Storage. Erro: ${it.message}"
                    )
                    updateUI(this.progressBar)
                }
        }else{
            updateUI(this.progressBar)
        }
    }

    private fun updateUI(progressBar: ProgressBar) {
        val auth = FirebaseAuth.getInstance()
        val loggedUser = auth.currentUser
        if (loggedUser != null) {
            val welcomeMessage = "Bem-vindo, ${loggedUser.email}!"
            Toast.makeText(contextFromActivityWhoCalled, welcomeMessage, Toast.LENGTH_SHORT).show()

            val intent = Intent(contextFromActivityWhoCalled, ListLoginsActivity::class.java)
            startActivity(contextFromActivityWhoCalled,intent, null)
        } else {
            val errorMessage = "Falha no registro."
            Toast.makeText(contextFromActivityWhoCalled, errorMessage, Toast.LENGTH_SHORT).show()
        }
        progressBar.visibility = View.GONE
    }
}