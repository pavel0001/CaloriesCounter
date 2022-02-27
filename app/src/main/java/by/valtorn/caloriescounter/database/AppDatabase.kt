package by.valtorn.caloriescounter.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.valtorn.caloriescounter.CaloriesCounterApp
import by.valtorn.caloriescounter.database.calories.CaloriesDay
import by.valtorn.caloriescounter.database.calories.CaloriesDAO

@Database(
    entities = [CaloriesDay::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun caloriesDayDAO(): CaloriesDAO

    companion object {
        val instance = Room.databaseBuilder(CaloriesCounterApp.instance, AppDatabase::class.java, "calories-db")
            .enableMultiInstanceInvalidation()
            .fallbackToDestructiveMigration()
            .build()
    }
}