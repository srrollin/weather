package dev.rollin.weather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import dev.rollin.weather.service.ServiceFactory
import dev.rollin.weather.service.WeatherService
import dev.rollin.weather.constant.NetworkState
import dev.rollin.weather.constant.TempUnits
import dev.rollin.weather.model.Forecast
import kotlinx.coroutines.*

class CurrentLocationViewModel : ViewModel()
{
    private val TAG = "CurrentLocationViewModel"
    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)
    private var service = ServiceFactory.retrofit().create(WeatherService::class.java)

    val forecast = MutableLiveData<Forecast>()
    val state = MutableLiveData<NetworkState>()

    fun getForecast(lat: Double, lon: Double, units: String = TempUnits.C.unit)
    {
        coroutineScope.launch {
            val postRequest = service.getForecastByGeolocationAsync(lat, lon, units)
            withContext(Dispatchers.Main) {
                try {
                    state.value = NetworkState.LOADING
                    val response = postRequest.await()
                    if (response.isSuccessful) {
                        val data = response.body()
                        forecast.value = data
                        state.value = NetworkState.IDLE
                    } else {
                        Log.d(TAG, response.errorBody().toString())
                        state.value = NetworkState.ERROR
                    }

                } catch (e: Throwable) {
                    Log.d(TAG, e.message.toString())
                    state.value = NetworkState.ERROR
                }
            }
        }
    }
}
