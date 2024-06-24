package com.pudagane.weatherforecast.network

import com.pudagane.weatherforecast.model.ForecastResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface ForecastApi {
    @GET("daily")
    fun getReport(@Query("city") city:String,
                  @Query("key") key:String ): Single<ForecastResponse>

}