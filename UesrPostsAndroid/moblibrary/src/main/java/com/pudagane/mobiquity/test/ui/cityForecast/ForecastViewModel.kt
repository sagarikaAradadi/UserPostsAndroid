package com.pudagane.mobiquity.test.ui.cityForecast

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pudagane.mobiquity.test.network.WeatherForecastService
import com.pudagane.weatherforecast.model.Data
import com.pudagane.weatherforecast.model.ForecastResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ForecastViewModel : ViewModel() {

    private val forecastService = WeatherForecastService()
    private val disposable= CompositeDisposable()

    val weatherForcastList = MutableLiveData<ArrayList<Data>>()
    val dataLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(city:String){
        Log.d("ForecastViewModel", "refresh city=$city")
        fetchData(city)
    }
    private fun fetchData(city:String){
        loading.value= true

        disposable.add(
            forecastService.getReport(city,"c6ea77d9965849aba852974b9e156c22")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<ForecastResponse>(){
                    override fun onSuccess(response: ForecastResponse) {
                        Log.d("getReport", "response.toString()==$response")
                        weatherForcastList.value = response.data as ArrayList<Data>
                        dataLoadError.value=false
                        loading.value=false

                    }

                    override fun onError(e: Throwable) {
                        Log.d("getReport", "onError==$e")
                        dataLoadError.value=true
                        loading.value=false
                    }

                })
        )
    }
}