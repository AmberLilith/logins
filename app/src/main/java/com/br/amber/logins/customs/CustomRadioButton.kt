package com.br.amber.logins.customs

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioButton
import kotlin.properties.Delegates

class CustomRadioButton: androidx.appcompat.widget.AppCompatRadioButton {

    constructor(context: Context) : super(context) {
        // Inicialização de construtor
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // Inicialização de construtor com atributos
    }



    private var optionNumber = 0

    fun setOptionNumber(optionNumber: Int) {
        this.optionNumber = optionNumber
    }

    fun getOptionNumber(): Int {
        return this.optionNumber
    }


}