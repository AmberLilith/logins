package com.br.amber.logins.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.br.amber.logins.models.Login
import com.br.amber.logins.recycleviews.ListLoginsAdapter
import com.br.amber.logins.services.UserService
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
    private lateinit var loogedUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.br.amber.logins.R.layout.activity_list_logins)

        loggedUserImageViewPicture = findViewById(com.br.amber.logins.R.id.listLoginsImageViewLoggedUserPicture)
        recyclerView = findViewById(com.br.amber.logins.R.id.listLoginsRecyclerViewLogins)
        buttonNewLogin = findViewById(com.br.amber.logins.R.id.listLoginsFloatingActionButtonCreateNewLogin)

        val databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        loogedUser = auth.currentUser!!
        val path = loogedUser.uid
        val plataformsNames = mutableListOf<String>()
        val loginsKeys = mutableListOf<String>()
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


        buttonNewLogin.setOnClickListener {
            val intent = Intent(this, CreateOrEditLoginActivity::class.java)
            intent.putExtra("parameters", "{\"method\":\"create\", \"loginKey\":\"\"}")
            startActivity(intent)
        }

        loggedUserImageViewPicture.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(com.br.amber.logins.R.menu.pop_up_menu, popupMenu.menu)

            val menuItem = popupMenu.menu.findItem(com.br.amber.logins.R.id.popUpMenuLoggedUserName)
            val userService = UserService()
            userService.getUserDatas{ retrievedUser ->
                if(retrievedUser != null){
                    menuItem.title = "Olá, ${retrievedUser.getFistName()}!"
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


        uploadLoggedUserPicture()

    }

    private fun uploadLoggedUserPicture(){
        //TODO Carregar imagem do usuário logado vinda do Cloud Storage. Nada do que eu tentei deu certo. Quando carrega a imagem, o ImageView simplesmente some da tela


        loggedUserImageViewPicture.setImageResource(com.br.amber.logins.R.drawable.baseline_person_2_24)

        /*val loggedUserPictureUri: Uri = Uri.parse("gs://logins-61353.appspot.com/images/ela.jpg")
        Glide.with(this)
            .load(loggedUserPictureUri)
            .into(loggedUserImageViewPicture)*/




    /*val imagePath = loggedUserPictureUri.lastPathSegment?.take(100) ?: ""
    val storage = FirebaseStorage.getInstance()
    val imageRef = storage.reference.child("images").child("${loogedUser.uid}.jpg")

    // Faz o download da imagem para o dispositivo
    imageRef.downloadUrl
        .addOnSuccessListener { uri ->
            // A imagem foi baixada com sucesso

            // Exibe a imagem no ImageView
            loggedUserImageViewPicture.setImageURI(uri)
        }
        .addOnFailureListener {
            Log.e(this.javaClass.simpleName, it.message!!)
        }

            imagemGlide.with(this)
                .load(loggedUserPictureUri)
                .into(loggedUserImageViewPicture)*/


    }

}