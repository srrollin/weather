package dev.rollin.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Alert(
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "expires")
    val expires: Int? = null,
    @Json(name = "regions")
    val regions: List<String?>? = null,
    @Json(name = "severity")
    val severity: String? = null,
    @Json(name = "time")
    val time: Int? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "uri")
    val uri: String? = null
)