package com.example.myapplication.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.spec.SecretKeySpec

object AESEncrypt {
    fun encryptfile(inputPath: String, outputPath: String, password: String) {
        if(!File(inputPath).exists()) File(inputPath).createNewFile()
        if(!File(outputPath).exists()) File(outputPath).createNewFile()

        val fis = FileInputStream(inputPath)
        val fos = FileOutputStream(outputPath)
        var key: ByteArray = password.toByteArray(charset("UTF-8"))
        val sha: MessageDigest = MessageDigest.getInstance("SHA-1")
        key = sha.digest(key)
        key = key.copyOf(16)

        val sks = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, sks)

        val cos = CipherOutputStream(fos, cipher)
        var b: Int
        val d = ByteArray(8)
        while (fis.read(d).also { b = it } != -1) {
            cos.write(d, 0, b)
        }
        cos.flush()
        cos.close()
        fis.close()

        File(inputPath).delete()
    }

    fun decrypt(inputPath: String, outPath: String, password: String) {
        if(!File(inputPath).exists()) File(inputPath).createNewFile()
        if(!File(outPath).exists()) File(outPath).createNewFile()

        val fis = FileInputStream(inputPath)
        val fos = FileOutputStream(outPath)
        var key: ByteArray = password.toByteArray(charset("UTF-8"))
        val sha: MessageDigest = MessageDigest.getInstance("SHA-1")
        key = sha.digest(key)
        key = key.copyOf(16)
        val sks = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, sks)
        val cis = CipherInputStream(fis, cipher)
        var b: Int
        val d = ByteArray(8)

        while (cis.read(d).also { b = it } != -1) {
            fos.write(d, 0, b)
        }

        fos.flush()
        fos.close()
        cis.close()

        File(inputPath).delete()
    }
}
