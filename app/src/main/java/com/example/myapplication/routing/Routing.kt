package com.example.myapplication.routing

import android.app.Activity
import com.example.myapplication.BuildConfig
import com.example.myapplication.ui.MainCalculatorActivity
import com.example.myapplication.R
import com.example.myapplication.extension.showToast
import com.example.myapplication.util.Constant
import com.example.myapplication.util.PermissionUtil

fun Activity.getCurrentActivity(): MainCalculatorActivity {
    return this as MainCalculatorActivity
}

fun Activity.selectPicker() {
    when(BuildConfig.FLAVOR) {
        Constant.GREEN_CAMERA -> getCurrentActivity().openCamera()
        Constant.RED_CAMERA -> getCurrentActivity().openCamera()
        Constant.GREEN_GALLERY -> getCurrentActivity().openGallery()
        Constant.RED_GALLERY -> getCurrentActivity().openGallery()
    }
}

fun Activity.showErrorPermission() {
    when(BuildConfig.FLAVOR) {
        Constant.GREEN_CAMERA -> this.getString(R.string.error_camera_permission)
        Constant.RED_CAMERA -> this.getString(R.string.error_camera_permission)
        Constant.GREEN_GALLERY -> this.getString(R.string.error_gallery_permission)
        Constant.RED_GALLERY -> this.getString(R.string.error_gallery_permission)
    }
}

fun Activity.askPermission() {
    when(BuildConfig.FLAVOR) {
        Constant.GREEN_CAMERA -> {
            val isGranted = PermissionUtil.checkAndRequestPermissionCamera(this)
            if(isGranted) getCurrentActivity().openCamera() else showToast(this.getString(R.string.error_camera_permission))
        }
        Constant.RED_CAMERA -> {
            val isGranted = PermissionUtil.checkAndRequestPermissionCamera(this)
            if(isGranted) getCurrentActivity().openCamera() else showToast(this.getString(R.string.error_camera_permission))
        }
        Constant.GREEN_GALLERY -> {
            val isGranted = PermissionUtil.checkAndRequestPermissionGallery(this)
            if(isGranted) getCurrentActivity().openGallery() else showToast(this.getString(R.string.error_gallery_permission))
        }
        Constant.RED_GALLERY -> {
            val isGranted = PermissionUtil.checkAndRequestPermissionGallery(this)
            if(isGranted) getCurrentActivity().openGallery() else showToast(this.getString(R.string.error_gallery_permission))
        }
    }
}

fun Activity.getRadioButtonStorageType(rbId: Int, onDatabaseSelected: () -> Unit = {}, onFileSelected :() -> Unit = {}) {
    when(rbId) {
        R.id.rbDatabaseStorage -> onDatabaseSelected()
        R.id.rbFileStorage -> onFileSelected()
    }
}