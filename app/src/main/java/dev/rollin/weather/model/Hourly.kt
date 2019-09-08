package dev.rollin.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hourly(
    @Json(name = "data")
    val `data`: List<DataX?>? = null,
    @Json(name = "icon")
    val icon: String? = null,
    @Json(name = "summary")
    val summary: String? = null
)