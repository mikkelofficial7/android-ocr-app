package com.example.myapplication.extension

import android.content.Context
import com.example.myapplication.util.AESEncrypt
import com.example.myapplication.util.Constant
import java.io.File

fun openFile(context: Context, password: String) : File {
    val fileEncrypt = File(context.filesDir, Constant.TXT_FILE_NAME_ENCRYPT)
    val file = File(context.filesDir, Constant.TXT_FILE_NAME)

    AESEncrypt.decrypt(fileEncrypt.path, file.path, password)

    return file
}

fun closeFile(context: Context, password: String) : File {
    val fileEncrypt = File(context.filesDir, Constant.TXT_FILE_NAME_ENCRYPT)
    val file = File(context.filesDir, Constant.TXT_FILE_NAME)

    AESEncrypt.encryptfile(file.path, fileEncrypt.path, password)

    return fileEncrypt
}