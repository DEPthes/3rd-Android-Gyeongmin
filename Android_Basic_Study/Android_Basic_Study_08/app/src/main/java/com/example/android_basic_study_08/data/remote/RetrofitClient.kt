package com.example.android_basic_study_08.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.unsplash.com"
    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

//    private val loggingInterceptor = if (BuildConfig.DEBUG) {
//        HttpLoggingInterceptor(PrettyJsonLogger()).setLevel(
//            HttpLoggingInterceptor.Level.BODY)
//    } else {
//        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
//    }
    private val loggingInterceptor = HttpLoggingInterceptor(PrettyJsonLogger()).setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(RequestInterceptor())
        .build()

    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }
        return instance!!
    }
}