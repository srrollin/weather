package dev.rollin.weather.util

import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class LocationUtil(private var activity: Activity)
{
    private lateinit var locationCallback: LocationCallback
    private var fusedLocationClient: FusedLocationProviderClient
            = LocationServices.getFusedLocationProviderClient(activity)

    val lastLocation =  MutableLiveData<Location>()
    val currentLocation =  MutableLiveData<Location>()
    val currentLocationAddress =  MutableLiveData<List<Address>>()

    init {
        setupLastKnownLocation()
        setupLocationCallBack()
    }

    private fun setupLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    currentLocation.value = location
                }
            }
        }
    }

    private fun setupLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                location?.let {
                    lastLocation.value = location
                }
            }
    }

    fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(LocationRequest(), locationCallback, null)
    }

    fun getAddressByCoordinates(lat: Double, lon: Double) {
        GlobalScope.launch(Dispatchers.Main) {
            val address = Geocoder(activity, Locale.getDefault()).getFromLocation(lat, lon, 1)
            currentLocationAddress.value = address
        }
    }
}