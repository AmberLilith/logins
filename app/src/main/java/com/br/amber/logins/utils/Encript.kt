package com.br.amber.logins.utils

import android.annotation.SuppressLint
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Encript {

    companion object {
        fun generateSecretKey(): String {
            val keyGenerator = KeyGenerator.getInstance("AES")
            keyGenerator.init(256) // Tamanho da chave
            val secretKey = keyGenerator.generateKey()
            val chaveBytes = secretKey.encoded
            return chaveBytes.toString(StandardCharsets.ISO_8859_1)

        }

        @SuppressLint("GetInstance")
        fun encrypt(dataToEncrypt: String, secretKeyString: String): ByteArray {
            val dataToEncryptByteArray = dataToEncrypt.toByteArray()
            val secretKeyByteArray = secretKeyString.toByteArray()
            val secretKey = SecretKeySpec(secretKeyByteArray, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val iv = ByteArray(16) // Initialization vector
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
            return cipher.doFinal(dataToEncryptByteArray)
        }


        fun decrypt(encryptedData: String, secretKeyString: String): ByteArray {
            val encryptedDataByteArray = encryptedData.toByteArray()
            val secretKeyByteArray = secretKeyString.toByteArray()
            val secretKey = SecretKeySpec(secretKeyByteArray, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val iv = ByteArray(16) // Initialization vector
            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
            return cipher.doFinal(encryptedDataByteArray)
        }

    }

}


    /*   @SuppressLint("GetInstance")
       fun encrypt(dataToEncrypt: String, secretKeyString: String): ByteArray {
           val dataToEncryptByteArray = dataToEncrypt.toByteArray()
           val secretKeyByteArray = secretKeyString.toByteArray()
           val secretKey = SecretKeySpec(secretKeyByteArray, "AES")
           val cipher = Cipher.getInstance("AES/ECB/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return cipher.doFinal(dataToEncryptByteArray)
       }


       fun decrypt(encryptedData: String, secretKeyString: String): ByteArray {
           val encryptedDataByteArray = encryptedData.toByteArray()
           val secretKeyByteArray =secretKeyString.toByteArray()
           val secretKey = SecretKeySpec(secretKeyByteArray, "AES")
           val cipher = Cipher.getInstance("AES/ECB/NoPadding")
           cipher.init(Cipher.DECRYPT_MODE, secretKey)
           return cipher.doFinal(encryptedData.toByteArray())
       }

       private fun getSavedSecretKey(): String {
           return ""

       }
   }
}*/



/*class Encript {

    companion object {

        private val iv: ByteArray = byteArrayOf(0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f)

        fun generateSecretKey(alias: String): SecretKey {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).run {
                setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                setRandomizedEncryptionRequired(true)
                setKeySize(256)
                build()
            }
            keyGenerator.init(keyGenParameterSpec)
            return keyGenerator.generateKey()
        }

        fun encryptData(dataToEncrypt: ByteArray, key: SecretKey): Pair<ByteArray, ByteArray> {
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val encryptedData = cipher.doFinal(dataToEncrypt)
            return Pair(encryptedData, Encript.iv)
        }

        fun decryptData(encryptedData: ByteArray, key: SecretKey): ByteArray {
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            val ivSpec = IvParameterSpec(Encript.iv)
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
            return cipher.doFinal(encryptedData)
        }

    }
}*/


/*class Encript {

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

        fun encryptData(dataToEncrypt: ByteArray, key: SecretKeySpec): Pair<ByteArray, ByteArray> {
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val iv = cipher.iv
            val encryptedData = cipher.doFinal(dataToEncrypt)
            return Pair(encryptedData, iv)
        }

        fun decryptData(encryptedData: ByteArray, key: SecretKeySpec, iv: ByteArray): ByteArray {
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
            return cipher.doFinal(encryptedData)
        }

    }
}*/

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
