package com.pudagane.mobiquity.test.db.repository

import android.app.Application
import com.pudagane.mobiquity.test.db.WeatherDatabase
import com.pudagane.mobiquity.test.db.dao.CityDao
import com.pudagane.mobiquity.test.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class CityRepository(application: Application) : CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var cityDao: CityDao?

    init {
        val db = WeatherDatabase.getDatabase(application)
        cityDao = db?.cityDao()
    }

    fun getCities() = cityDao?.getCities()

    fun addCity(city: City) {
            launch  { addCityBG(city) }
    }

    private suspend fun addCityBG(city: City){
       withContext(Dispatchers.IO){
           cityDao?.addCity(city)
       }
    }

   suspend fun deleteById(id:Int){
        withContext(Dispatchers.IO){
            cityDao?.deleteById(id)
        }
    }



}
