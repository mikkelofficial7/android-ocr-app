package com.example.myapplication.koin.koinmodule

import android.content.Context
import com.example.myapplication.BuildConfig
import com.example.myapplication.extension.closeFile
import com.example.myapplication.util.Constant
import java.io.File

object FileModule {
    fun provideFile(context: Context, secretKey: String) : File {
       val file = File(context.filesDir, Constant.TXT_FILE_NAME)
       if(!file.exists()) file.createNewFile()

       closeFile(context, secretKey)
       return file
    }

    fun provideFileSecretKey() : String{
        return BuildConfig.FILE_ENCRYPTOR_KEY
    }
}