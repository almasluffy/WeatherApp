package com.example.weatherapplication.presentation.weather.di

import android.util.Log
import com.example.weatherapplication.data.api_services.WeatherApi
import com.example.weatherapplication.data.repository.WeatherRepositoryImpl
import com.example.weatherapplication.data.weatherDatabase.WeatherDao
import com.example.weatherapplication.data.weatherDatabase.WeatherRoomDatabase
import com.example.weatherapplication.domain.repository.WeatherRepository
import com.example.weatherapplication.presentation.weather.detail.WeatherDetailViewModel
import com.example.weatherapplication.presentation.weather.list.WeatherListViewModel
import com.example.weatherapplication.utils.AppConstants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
//import com.mobile.coroutineapplication.data.respository.MovieRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single(named("appid")) { provideApiKey() }
    single(named("base_url")) { provideBaseUrl() }
    single { provideHttpLoggingInterceptor() }
    single { provideStethoInterceptor() }
    single { provideAuthInterceptor(appid = get(named("appid"))) }
    single { provideOkHttp(
        loggingInterceptor = get(),
        stethoInterceptor = get(),
        authInterceptor = get()
    )}
    single { provideCallAdapterFactory() }
    single { provideConverterFactory() }
    single { provideRetrofit(
        baseUrl = get(named("base_url")),
        okHttpClient = get(),
        gsonConverterFactory = get(),
        callAdapterFactory = get()
    )}
    single { provideMovieApi(retrofit = get()) }
}

val repositoryModule = module {
    single { provideWeatherRepository(weatherApi = get(), weatherDao = get()) }
}

val roomModule = module {
    single { WeatherRoomDatabase.getDatabase(
        context = androidApplication(),
        scope = get()
    ) }

    single(createdAtStart = false) { get<WeatherRoomDatabase>().weatherDao() }

    factory { SupervisorJob() }
    factory { CoroutineScope(Dispatchers.IO) }
}

val viewModelModule = module {
    viewModel { WeatherListViewModel(weatherRepository = get())}
    viewModel { WeatherDetailViewModel(weatherRepository = get()) }
}

val appModule = listOf(networkModule, repositoryModule, viewModelModule, roomModule)

//--------------------------------------Repository--------------------------------------------------

fun provideWeatherRepository(weatherApi: WeatherApi, weatherDao: WeatherDao): WeatherRepository = WeatherRepositoryImpl(weatherApi, weatherDao)


//--------------------------------------Network-----------------------------------------------------

fun provideApiKey(): String = AppConstants.API_KEY

fun provideBaseUrl(): String = AppConstants.BASE_URL

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger { message -> Log.d("OkHttp", message)}
    ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideStethoInterceptor(): StethoInterceptor = StethoInterceptor()

fun provideAuthInterceptor(appid: String): Interceptor {
    return Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("appid", appid)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
}

fun provideOkHttp(
    loggingInterceptor: HttpLoggingInterceptor,
    stethoInterceptor: StethoInterceptor,
    authInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(stethoInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()
}

fun provideCallAdapterFactory(): CallAdapter.Factory = CoroutineCallAdapterFactory()

fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

fun provideRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    gsonConverterFactory: Converter.Factory,
    callAdapterFactory: CallAdapter.Factory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()
}

fun provideMovieApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)


