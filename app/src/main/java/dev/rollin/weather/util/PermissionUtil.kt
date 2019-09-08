package dev.rollin.weather.util

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData

class PermissionUtil(private var activity: AppCompatActivity)
{
    private val PERMISSION_REQUEST = 0

    val permissionResponse = MutableLiveData<Boolean>()

    fun hasPermission(permission: String): Boolean {
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(arrayOfPermissions: Array<String>) {
        activity.requestPermissions(arrayOfPermissions, PERMISSION_REQUEST)
    }

    fun onRequestPermissionsResult(grantResults: IntArray) {
        permissionResponse.value = grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED
    }
}