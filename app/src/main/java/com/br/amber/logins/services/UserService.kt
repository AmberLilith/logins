package com.br.amber.logins.services

import android.util.Log
import com.br.amber.logins.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserService {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val currentUser = firebaseAuth.currentUser
    private val userId = currentUser!!.uid
    private val database = FirebaseDatabase.getInstance()
    private val userNodeReference = database.getReference(userId).child("user")

    fun getUser(callback: (User?) -> Unit){
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
                        Log.e(this.javaClass.simpleName,"Erro ao recuperar dados: ${databaseError.message}")
                        callback(null)
                    }
                })
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName,"Não foi possível recuperar o login! Erro: ${e.message}")
        }
    }

    fun getSecretKey(callback: (String?) -> Unit){
        try {
            userNodeReference.child("secretKey")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val secretKey = dataSnapshot.getValue(String::class.java)
                            callback(secretKey)
                        } else {
                            Log.d(this.javaClass.simpleName,"Registro não existe")
                            callback(null)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(this.javaClass.simpleName,"Erro ao recuperar dados: ${databaseError.message}")
                        callback(null)
                    }
                })
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName,"Não foi possível recuperar o login! Erro: ${e.message}")
        }
    }

    fun getFistName(wholeName:String): String{
        val names = wholeName.split(" ")
        return names[0]
    }


}