package com.example.myapplication.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


object PermissionUtil {
    fun checkAndRequestPermissionCamera(activity: Activity): Boolean {
        val permissions = arrayOf(
            "android.permission.CAMERA",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

        val permissionsList: MutableList<String> = ArrayList()
        for (permission in permissions) {
            val permissionState = activity.checkSelfPermission(permission)
            if (permissionState == PackageManager.PERMISSION_DENIED) {
                permissionsList.add(permission)
            }
        }
        if (permissionsList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                permissionsList.toTypedArray(),
                Constant.PERMISSION_REQ_CODE
            )
            return false
        }
        return true
    }

    fun checkAndRequestPermissionGallery(activity: Activity): Boolean {
        val permissions = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

        val permissionsList: MutableList<String> = ArrayList()
        for (permission in permissions) {
            val permissionState = activity.checkSelfPermission(permission)
            if (permissionState == PackageManager.PERMISSION_DENIED) {
                permissionsList.add(permission)
            }
        }

        if (permissionsList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                permissionsList.toTypedArray(),
                Constant.PERMISSION_REQ_CODE
            )
            return false
        }
        return true
    }


    fun onRequestPermissionsResult(
        activity: Activity?,
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        resultGranted: (Boolean) -> Unit
    ) {
        if (requestCode == Constant.PERMISSION_REQ_CODE && grantResults.isNotEmpty()) {
            val permissionsList: MutableList<String?> = ArrayList()
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionsList.add(permissions[i])
                }
            }
            if (permissionsList.isEmpty()) {
                resultGranted(true)
            } else {
                var showRationale = false
                for (permission in permissionsList) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            permission!!
                        )
                    ) {
                        showRationale = true
                        break
                    }
                }
                if (showRationale) {
                    resultGranted(false)
                }
            }
        }
    }
}