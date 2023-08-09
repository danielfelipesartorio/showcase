package com.sartorio.showcase.extensions

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

fun ComponentActivity.checkLocationPermissionAndExecute(
    successCallback: () -> Unit = {},
    deniedCallback: () -> Unit = {}
) = if (
    this.checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
) {
    val requestPermissionLauncher =
        this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGranted ->
            if (isGranted.values.all { it }) {
                successCallback.invoke()
            } else {
                deniedCallback.invoke()
            }
        }
    requestPermissionLauncher.launch(arrayOf(ACCESS_COARSE_LOCATION))
} else {
    successCallback.invoke()
}
