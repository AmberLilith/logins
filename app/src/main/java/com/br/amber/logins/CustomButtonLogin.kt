package com.br.amber.logins

import android.content.Context
import android.util.AttributeSet

class CustomButtonLogin : androidx.appcompat.widget.AppCompatButton {

    constructor(context: Context) : super(context) {
        // Inicialização de construtor
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // Inicialização de construtor com atributos
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        // Inicialização de construtor com atributos e estilo
    }

    private lateinit var loginKey: String

    fun setLoginKey(loginKey: String) {
        this.loginKey = loginKey
    }

    fun getLoginKey(): String {
        return this.loginKey
    }


}