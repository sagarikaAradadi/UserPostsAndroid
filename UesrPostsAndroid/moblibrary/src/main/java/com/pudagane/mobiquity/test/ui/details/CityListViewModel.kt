package com.pudagane.mobiquity.test.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pudagane.mobiquity.test.db.repository.CityRepository
import kotlinx.coroutines.launch

class CityListViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: CityRepository = CityRepository(application)

    fun getCities() = repository.getCities()

    fun remove(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }

}