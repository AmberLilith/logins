package com.br.amber.logins.utils

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.time.LocalDateTime
import java.util.UUID

class GeneralUse {

    companion object {
        fun showConfirmationDialog(context: Context, message: String, onConfirm: () -> Unit) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmação")
            builder.setMessage(message)

            builder.setPositiveButton("Sim") { dialog: DialogInterface, which: Int ->
                // Ação a ser realizada quando o usuário clica em "Sim"
                onConfirm()
                dialog.dismiss()
            }

            builder.setNegativeButton("Não") { dialog: DialogInterface, which: Int ->
                // Ação a ser realizada quando o usuário clica em "Não"
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        fun isEmailValid(email: String): Boolean {
            val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
            return email.matches(emailRegex.toRegex())
        }


        fun getRandomHash():String{
            return UUID.randomUUID().toString().replace("-","")
        }
    }
}