package com.example.weatherapplication.data.weatherDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Database(entities = [Weather::class], version = 2, exportSchema = false)
abstract class WeatherRoomDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WeatherRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherRoomDatabase::class.java,
                    "weather_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }

//    private class WeatherDatabaseCallback(
//        private val scope: CoroutineScope
//    ): RoomDatabase.Callback() {
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    populateDatabase(database.weatherDao())
//                }
//            }
//        }
//
//        suspend fun populateDatabase(weatherDao: WeatherDao) {
//            weatherDao.deleteAll()
//
//            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//            val currentDate = sdf.format(Date())
//
//            var weather = Weather(
//                1,
//                "Almaty",
//                264.15,
//                264.15,
//                264.15,
//                260.39,
//                1031,
//                currentDate
//            )
//
//            weatherDao.insert(weather)
//        }
//    }
}