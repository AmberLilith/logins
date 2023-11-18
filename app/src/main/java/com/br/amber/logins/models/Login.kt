package com.br.amber.logins.models

class Login(
    var id: String,
    val plataformName: String,
    val user: String,
    val password: String
) {
    constructor() : this("", "", "","") //Construtor vazio é usado na classe ListLoginsActivity para recuperar a lista de Logins do banco.

}