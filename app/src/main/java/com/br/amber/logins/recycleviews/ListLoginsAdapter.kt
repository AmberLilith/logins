package com.br.amber.logins.recycleviews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.br.amber.logins.CustomTextViewLogin
import com.br.amber.logins.R
import com.br.amber.logins.activities.CreateOrEditLoginActivity
import com.br.amber.logins.utils.GeneralUse

class ListLoginsAdapter(
    private val context: Context,
    private val plataformsNames: List<String>,
    private val loginsKeys: List<String>
) : RecyclerView.Adapter<ListLoginsAdapter.ViewHolder>() {

    class ViewHolder(view: View, listLoginsAdapterContext: Context) : RecyclerView.ViewHolder(view) {
        val plataformName: CustomTextViewLogin = itemView.findViewById(R.id.plataformName)
        val buttonEditLogin: Button = itemView.findViewById(R.id.buttonEditLogin)
        val buttonDeleteLogin: Button = itemView.findViewById(R.id.buttonDeleteLogin)
        init {

            buttonEditLogin.setOnClickListener {
                val intent = Intent(listLoginsAdapterContext, CreateOrEditLoginActivity::class.java)
                intent.putExtra("parameters", "{\"method\":\"edit\", \"loginKey\":\"${plataformName.getLoginKey()}\"}")
                startActivity(listLoginsAdapterContext, intent, null)
            }

            buttonDeleteLogin.setOnClickListener {
                Toast.makeText(itemView.context, "Excluir", Toast.LENGTH_LONG).show()
            }
        }

        fun vincula(plataformName: String, loginKey: String) {
            val nome = itemView.findViewById<CustomTextViewLogin>(R.id.plataformName)
            nome.text = plataformName
            nome.setLoginKey(loginKey)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.login_item, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int = plataformsNames.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plataformName =  plataformsNames[position]
        val loginKey = loginsKeys[position]
        holder.vincula(plataformName, loginKey)
    }

}
