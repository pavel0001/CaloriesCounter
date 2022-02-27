package by.valtorn.caloriescounter.repository

import by.valtorn.caloriescounter.database.AppDatabase
import by.valtorn.caloriescounter.database.calories.CaloriesDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CaloriesRepository {
    private val caloriesDao = AppDatabase.instance.caloriesDayDAO()

    val currentCaloriesDay = caloriesDao.getCurrentCaloriesDay()

    suspend fun insertCaloriesDay(caloriesDay: CaloriesDay) = withContext(Dispatchers.IO) {
        caloriesDao.insertShift(caloriesDay)
    }

    suspend fun updateCaloriesDay(caloriesDay: CaloriesDay) = withContext(Dispatchers.IO) {
        caloriesDao.updateCaloriesDay(caloriesDay)
    }
}