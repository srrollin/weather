package dev.rollin.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Flags(
    @Json(name = "meteoalarm-license")
    val meteoalarmLicense: String? = null,
    @Json(name = "nearest-station")
    val nearestStation: Double? = null,
    @Json(name = "sources")
    val sources: List<String?>? = null,
    @Json(name = "units")
    val units: String? = null
)