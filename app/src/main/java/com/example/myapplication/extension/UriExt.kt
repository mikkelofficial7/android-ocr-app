package com.example.myapplication.extension

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.IOException

fun Uri.uriToBitmap(contentResolver: ContentResolver): Bitmap? {
    return try {
        val inputStream = contentResolver.openInputStream(this)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}