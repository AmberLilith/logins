package com.br.amber.logins.models

class Login(
    val plataformName: String,
    val user: String,
    val password: String
) {
    constructor() : this("", "", "") {

    }

}