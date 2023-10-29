package com.br.amber.logins.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.br.amber.logins.R
import com.br.amber.logins.models.Login
import com.br.amber.logins.recycleviews.ListLoginsAdapter
import com.br.amber.logins.utils.GeneralUse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListLoginsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_logins)


        val databaseReference = FirebaseDatabase.getInstance().getReference()
        val path = "amber_silva_gmail_com" //TODO pegar usuário logado no futuro
        val plataformsNames = mutableListOf<String>()
        val loginsKeys = mutableListOf<String>()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewLogins)
        val context: Context = this

        databaseReference.child(path).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val loginList = mutableListOf<Login>()

                    for (childSnapshot in dataSnapshot.children) {
                        val childData = childSnapshot.getValue(Login::class.java)
                        //TODO parei aqui na hora de pegar o nó do login e passar par o Adapter
                        loginsKeys.add(childSnapshot.key.toString())
                        if (childData != null) {
                            loginList.add(childData)
                        }
                    }

                    for (login in loginList) {
                        plataformsNames.add(login.plataformName)
                    }

                    recyclerView.adapter = ListLoginsAdapter(context, plataformsNames, loginsKeys)
                } else {
                    plataformsNames.add("Não há login cadastrado ainda!")
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                println("Erro ao recuperar dados: ${databaseError.message}")
            }
        })

        val buttonNewLogin = findViewById<FloatingActionButton>(R.id.floatingActionButtonCreateNewLogin)
        buttonNewLogin.setOnClickListener{
            val intent = Intent(this, CreateOrEditLoginActivity::class.java)
            intent.putExtra("parameters", "{\"method\":\"create\", \"loginKey\":\"\"}")
            startActivity(intent)
        }


    }
}