package dev.rollin.weather

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import dev.rollin.weather.util.PermissionUtil
import dev.rollin.weather.view.CurrentLocationFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback
{
    private lateinit var permissionUtil: PermissionUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionUtil = PermissionUtil(this)

        if (permissionUtil.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            startLocationFragment()
        else
            requestLocationPermission()

        permissionUtil.permissionResponse.observe(this, Observer {
            if (it)
                startLocationFragment()
        })

        btPermission.setOnClickListener { requestLocationPermission() }
    }

    private fun requestLocationPermission() {
        permissionUtil.requestPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
    }

    private fun startLocationFragment() {
        btPermission.isGone = true

        supportFragmentManager.commit {
            val fragment = CurrentLocationFragment.newInstance()
            add(R.id.frame_container, fragment)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        permissionUtil.onRequestPermissionsResult(grantResults)
    }
}
