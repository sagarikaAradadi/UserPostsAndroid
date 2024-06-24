package com.pudagane.mobiquity.test.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pudagane.mobiquity.test.db.dao.CityDao
import com.pudagane.mobiquity.test.model.City
import java.util.concurrent.Executors

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {


    abstract fun cityDao(): CityDao

    companion object {

        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase? {
            if (INSTANCE == null) {
                synchronized(WeatherDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WeatherDatabase::class.java, "weather_database"
                        )
                            .addCallback(object :Callback(){
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    Executors.newSingleThreadScheduledExecutor()
                                        .execute(Runnable {
                                            INSTANCE?.cityDao()
                                                ?.insertAll(listOf(City(1, "Pune"),
                                                    City(2, "Mumbai"),
                                                    City(3, "Hyderabad"),
                                                    City(4, "London"),
                                                    City(5, "Bangalore"),
                                                    City(6, "Kolkata"),
                                                    City(7, "Dubai"),
                                                    City(8, "New Delhi")
                                                ))
                                        })

                                }
                            })
                            .build()

                    }
                }
            }
            return INSTANCE
        }
    }


}