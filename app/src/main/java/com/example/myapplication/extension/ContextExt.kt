package com.example.myapplication.extension

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import com.example.myapplication.R
import com.example.myapplication.util.Constant
import java.io.File

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.getCameraResultUri(): Uri {
    val tempImagesDir = File(applicationContext.filesDir, Constant.TEMP_DIR)
    if (!tempImagesDir.exists()) tempImagesDir.mkdir()

    val tempImage = File(tempImagesDir, "${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        applicationContext,
        getString(R.string.file_provider),
        tempImage
    )
}

fun Context.launchCamera(result: ActivityResultLauncher<Uri>, uri: Uri){
    result.launch(uri)
}

fun Context.launchGallery(result: ActivityResultLauncher<String>) {
    result.launch("image/*")
}