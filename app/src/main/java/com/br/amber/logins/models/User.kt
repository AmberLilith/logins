package com.br.amber.logins.models

class User(
    val name: String,
    val photoUri: String
) {
    constructor(): this("","")

    fun getFistName(): String{
        val names = name.split(" ")
        return names[0]
    }
}