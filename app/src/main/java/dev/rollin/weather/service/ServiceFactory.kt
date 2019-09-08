package dev.rollin.weather.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceFactory
{
    private val loggingInterceptor =  HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client =
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build()

    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.darksky.net/forecast/a92bf2b74e3080eba79dbc7adbf49de5/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}