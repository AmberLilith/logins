package com.br.amber.logins.dialogs

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.br.amber.logins.R
import com.br.amber.logins.customs.CustomRadioButton
import kotlin.random.Random

class DialogGeneratePassword : DialogFragment() {

    private lateinit var buttonCancel: Button
    private lateinit var buttonGeneratePassword: Button
    private lateinit var radioButtonAlphaNumSpecialCharactersUpAndLowCase: CustomRadioButton
    private lateinit var radioButtonAlphaNumOnly: CustomRadioButton
    private lateinit var radioButtonOnlyNumbers: CustomRadioButton
    private lateinit var radioButtonOnlyLetters: CustomRadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var spinnerMaxLength: Spinner
    private lateinit var editTextToReceiveGeneratedPassword: EditText
    private lateinit var editTextRepeatToReceiveGeneratedPassword: EditText
    private var selectedRadioButtonOption = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_generate_password, container, false)
        buttonCancel = view.findViewById(R.id.modalResetPasswordButtonCancel)
        buttonGeneratePassword = view.findViewById(R.id.dialogGeneratePasswordButtonResetPassword)
        radioButtonAlphaNumSpecialCharactersUpAndLowCase =  view.findViewById(R.id.dialogGeneratePasswordRadioButtonAlphaNumSpecialCharactersUpAndLowCase)
        radioButtonAlphaNumOnly = view.findViewById(R.id.dialogGeneratePasswordRadioButtonAlphaNumOnly)
        radioButtonOnlyNumbers = view.findViewById(R.id.dialogGeneratePasswordRadioButtonOnlyNumbers)
        radioButtonOnlyLetters = view.findViewById(R.id.dialogGeneratePasswordRadioButtonOnlyLetters)
        radioGroup = view.findViewById(R.id.dialogGeneratePasswordRadioGroupPasswordFormats)
        spinnerMaxLength = view.findViewById(R.id.dialogGeneratePasswordSpinnerMaxLength)

        radioButtonAlphaNumSpecialCharactersUpAndLowCase.setOptionNumber(1)
        radioButtonAlphaNumOnly.setOptionNumber(2)
        radioButtonOnlyNumbers.setOptionNumber(3)
        radioButtonOnlyLetters.setOptionNumber(4)

        spinnerMaxLength.setSelection(6)

        buttonCancel.setOnClickListener {
            dismiss()
        }

        buttonGeneratePassword.setOnClickListener {
            val generatedPassword =
                generatePassword(getMaxLength())
            editTextToReceiveGeneratedPassword.text =
                Editable.Factory.getInstance().newEditable(generatedPassword)
            editTextRepeatToReceiveGeneratedPassword.text =
                Editable.Factory.getInstance().newEditable(generatedPassword)
            dismiss()
        }


        radioGroup.setOnCheckedChangeListener { group, checkedId  ->
            val selectedRadioButton: CustomRadioButton = view.findViewById(checkedId)
            selectedRadioButtonOption = selectedRadioButton.getOptionNumber()
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

    private fun getMaxLength(): Int{
       return  spinnerMaxLength.selectedItem.toString().toInt()
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
        val specialCharacters =
            listOf("!", "#", "%", "&", "*", "(", ")", "/", ":", ";", ".", ",", "=", "-")
        val numbers = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

        val listOfCharacters = mutableListOf<String>()


        when (selectedRadioButtonOption) {

            1 -> {
                repeat(passwordMaxLength) {
                    val randomCharacters = when (Random.nextInt(4)) {
                        0 -> lowCaseLetters[Random.nextInt(lowCaseLetters.size)]
                        1 -> upCaseLetters[Random.nextInt(upCaseLetters.size)]
                        2 -> specialCharacters[Random.nextInt(specialCharacters.size)]
                        else -> numbers[Random.nextInt(numbers.size)].toString()
                    }
                    listOfCharacters.add(randomCharacters)
                }
            }

            2 -> {
                repeat(passwordMaxLength) {
                    val randomChar = when (Random.nextInt(3)) {
                        0 -> lowCaseLetters[Random.nextInt(lowCaseLetters.size)]
                        1 -> upCaseLetters[Random.nextInt(upCaseLetters.size)]
                        else -> numbers[Random.nextInt(numbers.size)].toString()
                    }
                    listOfCharacters.add(randomChar)
                }
            }


            3 -> {
                repeat(passwordMaxLength) {
                    listOfCharacters.add(numbers[Random.nextInt(numbers.size)].toString())
                }
            }

            else -> {
                repeat(passwordMaxLength) {
                    val randomChar =
                        when (Random.nextInt(2)) {
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