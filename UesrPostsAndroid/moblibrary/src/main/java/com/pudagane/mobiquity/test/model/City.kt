package com.pudagane.mobiquity.test.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cityId") var id:Int,

    @ColumnInfo(name = "CityName")
    var cityName:String

)