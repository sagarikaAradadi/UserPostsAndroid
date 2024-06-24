package com.pudagane.mobiquity.test.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pudagane.mobiquity.test.model.City


@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(city: City)

    @Query("SELECT * from city ORDER BY CityName ASC")
    fun getCities() : LiveData<List<City>>

    @Query("DELETE FROM city")
    fun deleteAll()

    @Query("DELETE FROM city WHERE cityId = :cityId")
    fun deleteById(cityId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cities: List<City>)

}