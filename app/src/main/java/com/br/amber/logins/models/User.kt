package com.br.amber.logins.models

class User(
    val name: String,
    val secretKey: String,
    val aggregator: Int
) {
    constructor(): this("","", 0)

}