package com.aniket91.afiirm.restuarantviewer.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    const val REQUEST_CODE = 123

    private fun hasPermissionBeingGranted(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    fun requestPermission(activity: Activity, permission: String) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), REQUEST_CODE)
    }

    fun hasLocationPermissionGranted(activity: Activity): Boolean {
        return hasPermissionBeingGranted(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}