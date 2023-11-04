package com.br.amber.logins.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec


class Encript {

    companion object {

        fun generateSecretKey(alias: String): SecretKey {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).run {
                setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                setUserAuthenticationRequired(true)
                setRandomizedEncryptionRequired(true)
                setKeySize(256)
                build()
            }
            keyGenerator.init(keyGenParameterSpec)
            return keyGenerator.generateKey()
        }

        fun encryptData(dataToEncrypt: ByteArray, key: Key): Pair<ByteArray, ByteArray> {
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val iv = cipher.iv
            val encryptedData = cipher.doFinal(dataToEncrypt)
            return Pair(encryptedData, iv)
        }

        fun decryptData(encryptedData: ByteArray, key: Key, iv: ByteArray): ByteArray {
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
            return cipher.doFinal(encryptedData)
        }

    }
}

/*// Registrar o provedor Bouncy Castle
Security.addProvider(BouncyCastleProvider())

// Criar uma chave (exemplo)
val keyBytes = "1234567890123456".toByteArray()
val key = KeyParameter(keyBytes)

// Dados a serem criptografados
val dataToEncrypt = "Dados confidenciais".toByteArray()

// Criptografar os dados
val (encryptedData, iv) = encryptData(dataToEncrypt, key)

// Descriptografar os dados
val decryptedData = decryptData(encryptedData, key, iv)

// Converter os dados descriptografados de volta para String
val decryptedText = String(decryptedData)

println("Dados originais: ${String(dataToEncrypt)}")
println("Dados criptografados: ${String(encryptedData)}")
println("Dados descriptografados: $decryptedText")*/
