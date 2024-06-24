package com.pudagane.mobiquity.test.network

import com.pudagane.weatherforecast.model.ForecastResponse
import com.pudagane.weatherforecast.network.ForecastApi
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherForecastService {
   private val BASE_URL = "https://api.weatherbit.io/v2.0/forecast/"

    private val api:ForecastApi

    init {
        api=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ForecastApi::class.java)
    }

    fun getReport(city:String, key:String): Single<ForecastResponse>{
        return  api.getReport(city, key)
    }
}