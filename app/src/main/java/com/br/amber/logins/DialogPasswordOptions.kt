package com.br.amber.logins

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import kotlin.random.Random

class DialogPasswordOptions : DialogFragment() {

    private lateinit var buttonCancel: Button
    private lateinit var buttonGeneratePassword: Button
    private lateinit var radioButtonAlphaNumSpecialCharactersUpAndLowCase: RadioButton
    private lateinit var radioButtonAlphaNumOnly: RadioButton
    private lateinit var radioButtonOnlyNumbers: RadioButton
    private lateinit var radioButtonOnlyLetters: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var spinnerMinLength: Spinner
    private lateinit var spinnerMaxLength: Spinner
    private lateinit var editTextToReceiveGeneratedPassword: EditText
    private lateinit var editTextRepeatToReceiveGeneratedPassword: EditText
    private var selectedRadioButtonId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modal_password_options, container, false)
        buttonCancel = view.findViewById(R.id.modal_password_optionsButtonCancel)
        buttonGeneratePassword =
            view.findViewById(R.id.modal_password_optionsButtonGeneratePassword)
        radioButtonAlphaNumSpecialCharactersUpAndLowCase =
            view.findViewById(R.id.modal_password_optionsRadioButtonAlphaNumSpecialCharactersUpAndLowCase)
        radioButtonAlphaNumOnly =
            view.findViewById(R.id.modal_password_optionsRadioButtonAlphaNumOnly)
        radioButtonOnlyNumbers =
            view.findViewById(R.id.modal_password_optionsRadioButtonOnlyNumbers)
        radioButtonOnlyLetters =
            view.findViewById(R.id.modal_password_optionsRadioButtonOnlyLetters)
        radioGroup = view.findViewById(R.id.modal_password_optionsRadioGroupPasswordFormats)

        spinnerMinLength = view.findViewById(R.id.modal_password_optionsSpinnerMinLength)
        spinnerMaxLength = view.findViewById(R.id.modal_password_optionsSpinnerMaxLength)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(0.6f) // Ajuste a opacidade desejada (0.0 a 1.0)

        setMinAndMaxDefaultValues(view)




        buttonCancel.setOnClickListener {
            dismiss()
        }

        buttonGeneratePassword.setOnClickListener {
            val generatedPassword =
                generatePassword(spinnerMaxLength.selectedItem.toString().toInt())
            editTextToReceiveGeneratedPassword.text =
                Editable.Factory.getInstance().newEditable(generatedPassword)
            editTextRepeatToReceiveGeneratedPassword.text =
                Editable.Factory.getInstance().newEditable(generatedPassword)

            dismiss()
        }


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedRadioButtonId = checkedId
        }


        return view
    }


    fun showDialogPasswordOptions(
        editTextToReceiveGeneratedPassword: EditText,
        editTextRepeatToReceiveGeneratedPassword: EditText
    ) {
        this.editTextToReceiveGeneratedPassword = editTextToReceiveGeneratedPassword
        this.editTextRepeatToReceiveGeneratedPassword = editTextRepeatToReceiveGeneratedPassword
    }

    fun setMinAndMaxDefaultValues(view: View) {
        spinnerMinLength.setSelection(4)
        spinnerMaxLength.setSelection(11)
    }


    private fun generatePassword(passwordMaxLength: Int): String {
        val lowCaseLetters = listOf(
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z"
        )
        val upCaseLetters = listOf(
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z"
        )
        val specialCharacters = listOf("!", "#", "$", "%", "&", "*", "(", ")","\\","/",":",";",".",",","=","-")
        val numbers = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

        val listOfCharacters = mutableListOf<String>()


        when (selectedRadioButtonId) {
            2131231295 -> {
                repeat(passwordMaxLength) {
                    val randomChar = when (Random.nextInt(3)) {
                        0 -> lowCaseLetters[Random.nextInt(lowCaseLetters.size)]
                        1 -> upCaseLetters[Random.nextInt(upCaseLetters.size)]
                        else -> numbers[Random.nextInt(numbers.size)].toString()
                    }
                    listOfCharacters.add(randomChar)
                }
            }

            2131231296 -> {
                listOfCharacters.add(lowCaseLetters[Random.nextInt(lowCaseLetters.size)])
                listOfCharacters.add(upCaseLetters[Random.nextInt(upCaseLetters.size)])
                listOfCharacters.add(specialCharacters[Random.nextInt(specialCharacters.size)])
                listOfCharacters.add(numbers[Random.nextInt(numbers.size)].toString())
                repeat(passwordMaxLength - 4) {
                    val randomCharacters = when (Random.nextInt(4)) {
                        0 -> lowCaseLetters[Random.nextInt(lowCaseLetters.size)]
                        1 -> upCaseLetters[Random.nextInt(upCaseLetters.size)]
                        2 -> specialCharacters[Random.nextInt(specialCharacters.size)]
                        else -> numbers[Random.nextInt(numbers.size)].toString()
                    }
                    listOfCharacters.add(randomCharacters)
                }
            }

            2131231298 -> {
                repeat(passwordMaxLength - 4) {
                    listOfCharacters.add(numbers[Random.nextInt(numbers.size)].toString())
                }
            }

            else -> {
                repeat(passwordMaxLength - 4) {
                    val randomChar =
                        when (Random.nextInt(2)) { // 0 para minúsculas, 1 para maiúsculas, 2 para especiais
                            0 -> lowCaseLetters[Random.nextInt(lowCaseLetters.size)]
                            else -> upCaseLetters[Random.nextInt(upCaseLetters.size)]
                        }
                    listOfCharacters.add(randomChar)
                }
            }
        }

        listOfCharacters.shuffle()

        return listOfCharacters.joinToString("")

    }
}