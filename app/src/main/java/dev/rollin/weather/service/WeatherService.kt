package dev.rollin.weather.service

import dev.rollin.weather.model.Forecast
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService
{
    @GET("{latitude},{longitude}")
    fun getForecastByGeolocationAsync(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("units") units: String
    ) : Deferred<Response<Forecast>>
}