package dev.rollin.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataX(
    @Json(name = "apparentTemperature")
    val apparentTemperature: Double? = null,
    @Json(name = "cloudCover")
    val cloudCover: Double? = null,
    @Json(name = "dewPoint")
    val dewPoint: Double? = null,
    @Json(name = "humidity")
    val humidity: Double? = null,
    @Json(name = "icon")
    val icon: String? = null,
    @Json(name = "ozone")
    val ozone: Double? = null,
    @Json(name = "precipIntensity")
    val precipIntensity: Double? = null,
    @Json(name = "precipProbability")
    val precipProbability: Double? = null,
    @Json(name = "precipType")
    val precipType: String? = null,
    @Json(name = "pressure")
    val pressure: Double? = null,
    @Json(name = "summary")
    val summary: String? = null,
    @Json(name = "temperature")
    val temperature: Double? = null,
    @Json(name = "time")
    val time: Int? = null,
    @Json(name = "uvIndex")
    val uvIndex: Int? = null,
    @Json(name = "visibility")
    val visibility: Double? = null,
    @Json(name = "windBearing")
    val windBearing: Int? = null,
    @Json(name = "windGust")
    val windGust: Double? = null,
    @Json(name = "windSpeed")
    val windSpeed: Double? = null
)