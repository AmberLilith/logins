package com.br.amber.logins.services

import android.content.Context
import android.widget.Toast
import com.br.amber.logins.models.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginService {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val currentUser = firebaseAuth.currentUser
    private val userId = currentUser!!.uid
    private val database = FirebaseDatabase.getInstance()
    private val userNodeReference = database.getReference(userId).child("logins")
    private lateinit var transactionMessage: String

    fun createLogin(login: Login, context: Context) {
        try {
            val newLoginNode = userNodeReference.push()
            newLoginNode.setValue(login)
            transactionMessage = "Login cadastrado com sucesso!"
        } catch (e: Exception) {
            transactionMessage = "Não foi possível salvar os dados!"
            println("${transactionMessage} Erro: ${e.message}")
        } finally {
            Toast.makeText(context, transactionMessage, Toast.LENGTH_LONG).show()
        }

    }

    fun editLogin(login: Login, context: Context, loginKey: String) {
        try {
            userNodeReference.child(loginKey).setValue(login)
            transactionMessage = "Alterações salvas com sucesso!"
        } catch (e: Exception) {
            transactionMessage = "Não foi possível salvar os dados!"
            println("${transactionMessage} Erro: ${e.message}")
        } finally {
            Toast.makeText(context, transactionMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun getLoginForEdition(context: Context, loginKey: String, callback: (Login?) -> Unit) {
        try {
            userNodeReference.child(loginKey)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val login = dataSnapshot.getValue(Login::class.java)
                            callback(login)
                        } else {
                            println("O nó $loginKey não existe")
                            callback(null)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        println("Erro ao recuperar dados: ${databaseError.message}")
                        callback(null)
                    }
                })
        } catch (e: Exception) {
            println("Não foi possível recuperar o login! Erro: ${e.message}")
        }

    }

    fun deleteLogin(context: Context, loginKey: String) {
        try {
            val updateMap = mapOf(loginKey to null)
            userNodeReference.updateChildren(updateMap)
            transactionMessage = "Login excluído!"
        } catch (e: Exception) {
            transactionMessage = "Não foi possível deletar o login!"
            println("${transactionMessage} Erro: ${e.message}")
        } finally {
            Toast.makeText(context, transactionMessage, Toast.LENGTH_LONG).show()
        }
    }

}