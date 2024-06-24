package com.pudagane.mobiquity.test.ui.selectLocation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.pudagane.mobiquity.test.db.repository.CityRepository
import com.pudagane.mobiquity.test.model.City

class SelectLocationFromMagViewModel  (application: Application) : AndroidViewModel(application) {

    private var repository: CityRepository = CityRepository(application)

    fun saveCity(city: String) {
        repository.addCity(City(0,city))
    }
}