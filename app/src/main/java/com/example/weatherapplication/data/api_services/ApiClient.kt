
package com.example.weatherapplication.data.api_services

import android.util.Log
import com.example.weatherapplication.utils.AppConstants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val apiClient: WeatherApi by lazy {
        val logging = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message -> Log.d("OkHttp", message)}
        ).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttp = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(logging)
            .addInterceptor {chain ->
                val newUrl = chain.request().url()
                    .newBuilder()
                    .addQueryParameter("appid",
                        AppConstants.API_KEY
                    )
                    .build()
                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return@lazy retrofit.create(WeatherApi::class.java)
    }
}

