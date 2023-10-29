package com.br.amber.logins.utils

class GeneralUse {

    companion object{
        fun replaceSpecialCharacters(value: String): String{
            return value.replace(Regex("[!@#\$%&*()\\-+=\\[{}\\]>.;:/?|, ]"), "_").lowercase()
        }
    }
}