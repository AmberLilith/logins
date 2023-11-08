package com.br.amber.logins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.br.amber.logins.utils.GeneralUse
import com.google.firebase.auth.FirebaseAuth

class DialogResetPassoword: DialogFragment() {

    private lateinit var editTextEmail: TextView
    private lateinit var buttonCancel: Button
    private lateinit var buttonSend: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_reset_password, container, false)
        auth = FirebaseAuth.getInstance()
        editTextEmail = view.findViewById(R.id.modalResetPasswordEditTextEmail)
        buttonCancel = view.findViewById(R.id.modalResetPasswordButtonCancel)
        buttonSend = view.findViewById(R.id.dialogGeneratePasswordButtonResetPassword)

        buttonCancel.setOnClickListener {
            dismiss()
        }

        buttonSend.setOnClickListener {
            val email = editTextEmail.text.toString()
            if(email.isEmpty() || !GeneralUse.isEmailValid(email)){
                editTextEmail.error = "Um email válido precisa ser informado!"
            }else{
                resetPassword(email)
            }
        }
        return view
    }

    private fun resetPassword(email: String) {
        val context = requireContext()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                    task -> if (task.isSuccessful) {
                                dismiss()
                                Toast.makeText(context, "Email enviado!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Não foi possível enviar o email!", Toast.LENGTH_SHORT).show()
                            }
            }
    }
}