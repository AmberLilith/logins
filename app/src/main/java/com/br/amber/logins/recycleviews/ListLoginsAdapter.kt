package com.br.amber.logins.recycleviews

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.br.amber.logins.CustomButtonLogin
import com.br.amber.logins.CustomTextViewLogin
import com.br.amber.logins.R
import com.br.amber.logins.activities.CreateOrEditLoginActivity
import com.br.amber.logins.services.LoginService
import com.br.amber.logins.utils.GeneralUse

class ListLoginsAdapter(
    private val context: Context,
    private val plataformsNames: MutableList<String>,
    private val loginsKeys: MutableList<String>
) : RecyclerView.Adapter<ListLoginsAdapter.ViewHolder>() {

    class ViewHolder(view: View, listLoginsAdapterContext: Context) :
        RecyclerView.ViewHolder(view) {
        val buttonEditLogin: CustomButtonLogin = itemView.findViewById(R.id.buttonEditLogin)
        val buttonDeleteLogin: CustomButtonLogin = itemView.findViewById(R.id.buttonDeleteLogin)

        init {

            buttonEditLogin.setOnClickListener {
                val intent = Intent(listLoginsAdapterContext, CreateOrEditLoginActivity::class.java)
                intent.putExtra(
                    "parameters",
                    "{\"method\":\"edit\", \"loginKey\":\"${buttonEditLogin.getLoginKey()}\"}"
                )
                startActivity(listLoginsAdapterContext, intent, null)
            }

            buttonDeleteLogin.setOnClickListener {
                GeneralUse.showConfirmationDialog(
                    listLoginsAdapterContext, "Quer realmente excluir esse login?"
                ) {
                    val loginService = LoginService()
                    loginService.deleteLogin(
                        listLoginsAdapterContext,
                        buttonDeleteLogin.getLoginKey()
                    )
                }
            }
        }

        fun vincula(plataformName: String, loginKey: String) {
            val plataformNameTextView =
                itemView.findViewById<CustomTextViewLogin>(R.id.plataformName)
            val buttonEditLogin = itemView.findViewById<CustomButtonLogin>(R.id.buttonEditLogin)
            val buttonDelete = itemView.findViewById<CustomButtonLogin>(R.id.buttonDeleteLogin)
            plataformNameTextView.text = plataformName
            buttonEditLogin.setLoginKey(loginKey)
            buttonDelete.setLoginKey(loginKey)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newPlataformsNames: List<String>, newLoginsKeys: List<String>) {
        plataformsNames.clear()
        plataformsNames.addAll(newPlataformsNames)
        loginsKeys.clear()
        loginsKeys.addAll(newLoginsKeys)
        notifyDataSetChanged() // Notifica o adaptador sobre as mudanças
    }

    // Método para limpar os dados
    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        plataformsNames.clear()
        loginsKeys.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.login_item, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int = plataformsNames.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plataformName = plataformsNames[position]
        val loginKey = loginsKeys[position]
        holder.vincula(plataformName, loginKey)
    }

}
