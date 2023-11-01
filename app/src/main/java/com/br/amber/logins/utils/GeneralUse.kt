package com.br.amber.logins.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

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


    }
}