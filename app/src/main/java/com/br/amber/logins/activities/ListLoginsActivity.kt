package com.br.amber.logins.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.br.amber.logins.R
import com.br.amber.logins.models.Login
import com.br.amber.logins.recycleviews.ListLoginsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListLoginsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_logins)


        val databaseReference = FirebaseDatabase.getInstance().getReference()
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val path = user!!.uid
        val plataformsNames = mutableListOf<String>()
        val loginsKeys = mutableListOf<String>()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewLogins)
        val context: Context = this

        databaseReference.child(path).child("logins")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val loginList = mutableListOf<Login>()
                        loginsKeys.clear()
                        for (childSnapshot in dataSnapshot.children) {
                            val childData = childSnapshot.getValue(Login::class.java)
                            loginsKeys.add(childSnapshot.key.toString())
                            if (childData != null) {
                                loginList.add(childData)
                            }
                        }

                        val newPlataformsNames = loginList.map { it.plataformName }
                        val newLoginsKeys = loginsKeys

                        if (plataformsNames.isEmpty()) {
                            recyclerView.adapter = ListLoginsAdapter(
                                context,
                                newPlataformsNames.toMutableList(),
                                newLoginsKeys.toMutableList()
                            )
                        } else {
                            (recyclerView.adapter as ListLoginsAdapter).updateData(
                                newPlataformsNames,
                                newLoginsKeys
                            )
                        }
                    } else {
                        (recyclerView.adapter as ListLoginsAdapter).clearData()
                    }
                }


                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(this.javaClass.name, "Erro ao recuperar dados: ${databaseError.message}")
                }
            })

        val buttonNewLogin =
            findViewById<FloatingActionButton>(R.id.floatingActionButtonCreateNewLogin)
        buttonNewLogin.setOnClickListener {
            val intent = Intent(this, CreateOrEditLoginActivity::class.java)
            intent.putExtra("parameters", "{\"method\":\"create\", \"loginKey\":\"\"}")
            startActivity(intent)
        }


    }
}