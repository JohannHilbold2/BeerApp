package com.johann.brewdogbeerchallenge.koin.retrofit

import com.johann.brewdogbeerchallenge.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceImpl {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
    }
    val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory()) // Most generic adapter last
        .build()


    inline fun <reified T : Any> createService(): T =
        Retrofit.Builder()
            .baseUrl("https://api.punkapi.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(T::class.java)
}