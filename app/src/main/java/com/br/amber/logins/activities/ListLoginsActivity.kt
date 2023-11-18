package com.br.amber.logins.activities


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
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

        val databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        loogedUser = auth.currentUser!!
        val path = loogedUser.uid
        val plataformsNames = mutableListOf<String>()
        val loginsIds = mutableListOf<String>()
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
                            loginsIds.add(childSnapshot.key.toString())
                            if (childData != null) {
                                loginList.add(childData)
                            }
                        }
                        // TODO ordenar a lista de plataformName em ordem alfabética sem que loginsIds fique desencontrado porque ambos tem que estar na mesma ordem
                        val newPlataformsNames = loginList.map { it.plataformName }.toMutableList()
                        newPlataformsNames.remove("Não exlcuir esse registro!!!")

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


        //uploadLoggedUserPicture()

    }

    private fun uploadLoggedUserPicture(){
        //TODO Carregar imagem do usuário logado vinda do Cloud Storage. Nada do que eu tentei deu certo. Quando carrega a imagem, o ImageView simplesmente some da tela

        val loggedUserPictureUri: Uri = Uri.parse("gs://logins-61353.appspot.com/images/${loogedUser.uid}/${loogedUser.uid}.jpg")

        loggedUserImageViewPicture.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override

            fun

                    onGlobalLayout() {
                loggedUserImageViewPicture.viewTreeObserver.removeOnGlobalLayoutListener(this)
                uploadLoggedUserPicture()
            }
        })
        loggedUserImageViewPicture.scaleType = ImageView.ScaleType.FIT_CENTER
    }

    /*private fun uploadLoggedUserPicture(){
        //TODO Carregar imagem do usuário logado vinda do Cloud Storage. Nada do que eu tentei deu certo. Quando carrega a imagem, o ImageView simplesmente some da tela

        val loggedUserPictureUri: Uri = Uri.parse("gs://logins-61353.appspot.com/images/${loogedUser.uid}/${loogedUser.uid}.jpg")
        Glide.with(this)
            .load(loggedUserPictureUri)
            .into(loggedUserImageViewPicture)


    }*/

}