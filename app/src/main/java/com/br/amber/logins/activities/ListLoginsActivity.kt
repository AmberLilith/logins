package com.br.amber.logins.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.br.amber.logins.models.Login
import com.br.amber.logins.recycleviews.ListLoginsAdapter
import com.br.amber.logins.services.UserService
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class ListLoginsActivity : AppCompatActivity() {

    private lateinit var loggedUserImageViewPicture: ImageView
    private lateinit var  recyclerView: RecyclerView
    private lateinit var buttonNewLogin: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var loogedUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.br.amber.logins.R.layout.activity_list_logins)

        loggedUserImageViewPicture = findViewById(com.br.amber.logins.R.id.listLoginsImageViewLoggedUserPicture)
        recyclerView = findViewById(com.br.amber.logins.R.id.listLoginsRecyclerViewLogins)
        buttonNewLogin = findViewById(com.br.amber.logins.R.id.listLoginsFloatingActionButtonCreateNewLogin)
        progressBar = findViewById(com.br.amber.logins.R.id.listLoginProgressBar)

        retrieveListOfLogins()


        buttonNewLogin.setOnClickListener {
            val intent = Intent(this, CreateOrEditLoginActivity::class.java)
            intent.putExtra("parameters", "{\"method\":\"create\", \"loginKey\":\"\"}")
            startActivity(intent)
        }

        loggedUserImageViewPicture.setOnClickListener { view ->
            println(loggedUserImageViewPicture.drawable)
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(com.br.amber.logins.R.menu.pop_up_menu, popupMenu.menu)

            val menuItem = popupMenu.menu.findItem(com.br.amber.logins.R.id.popUpMenuLoggedUserName)
            val userService = UserService()
            userService.getUser{ retrievedUser ->
                if(retrievedUser != null){
                    menuItem.title = "Olá, ${userService.getFistName(retrievedUser.name)}!"
                }
            }



            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    com.br.amber.logins.R.id.popUpMenuCriarNovoLogin -> {
                        val intent = Intent(this, CreateOrEditLoginActivity::class.java)
                        intent.putExtra("parameters", "{\"method\":\"create\", \"loginKey\":\"\"}")
                        startActivity(intent)
                        true
                    }
                    com.br.amber.logins.R.id.popUpMenuLogOut -> {
                        auth.signOut()
                        val intent = Intent(this, AuthenticationActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }

    }

    override fun onStart() {
        super.onStart()
        uploadLoggedUserPicture()
    }


    private fun retrieveListOfLogins(){
        val databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        loogedUser = auth.currentUser!!
        val path = loogedUser.uid
        val plataformsNames = mutableListOf<String>()
        var loginsIds = mutableListOf<String>()
        val context: Context = this
        progressBar.visibility = View.VISIBLE
        databaseReference.child(path).child("logins")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val loginList = mutableListOf<Login>()
                        loginsIds.clear()
                        for (childSnapshot in dataSnapshot.children) {
                            val childData = childSnapshot.getValue(Login::class.java)
                            if (childData != null) {
                                loginList.add(childData)
                            }
                        }
                        val sortedLoginList = loginList.filter { it.plataformName != "Não exlcuir esse registro!!!"}.sortedBy { it.plataformName }
                        val newPlataformsNames = sortedLoginList.map { it.plataformName }.toMutableList()
                        loginsIds = sortedLoginList.map { it.id }.toMutableList()

                        if (plataformsNames.isEmpty()) {
                            recyclerView.adapter = ListLoginsAdapter(
                                context,
                                newPlataformsNames,
                                loginsIds
                            )
                            progressBar.visibility = View.GONE
                        } else {
                            (recyclerView.adapter as ListLoginsAdapter).updateData(
                                newPlataformsNames,
                                loginsIds
                            )
                            progressBar.visibility = View.GONE
                        }
                    } else {
                        (recyclerView.adapter as ListLoginsAdapter).clearData()
                        progressBar.visibility = View.GONE
                    }
                }


                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(this.javaClass.name, "Erro ao recuperar dados: ${databaseError.message}")
                    progressBar.visibility = View.GONE
                }
            })
    }


    private fun uploadLoggedUserPicture(){
        loggedUserImageViewPicture.setImageResource(com.br.amber.logins.R.drawable.baseline_person_2_24)
        val storage = FirebaseStorage.getInstance()
        val imageRef = storage.reference.child("images/${loogedUser.uid}").child("${loogedUser.uid}.jpg")

        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                Log.i(this.javaClass.simpleName, "Imagem carregada com sucesso!")
                Glide.with(this)
                   .load(uri)
                   .into(loggedUserImageViewPicture)
            }
            .addOnFailureListener {
                Log.e(this.javaClass.simpleName, it.message!!)
            }


    }

}