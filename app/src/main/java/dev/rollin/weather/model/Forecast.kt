package dev.rollin.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "alerts")
    val alerts: List<Alert?>? = null,
    @Json(name = "currently")
    val currently: Currently? = null,
    @Json(name = "daily")
    val daily: Daily? = null,
    @Json(name = "flags")
    val flags: Flags? = null,
    @Json(name = "hourly")
    val hourly: Hourly? = null,
    @Json(name = "latitude")
    val latitude: Double? = null,
    @Json(name = "longitude")
    val longitude: Double? = null,
    @Json(name = "offset")
    val offset: Int? = null,
    @Json(name = "timezone")
    val timezone: String? = null
)