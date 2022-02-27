package by.valtorn.caloriescounter.database.calories

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CaloriesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShift(caloriesDay: CaloriesDay): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCaloriesDay(caloriesDay: CaloriesDay)

    @Query("SELECT * FROM CaloriesDay WHERE date IS NOT NULL")
    fun getCurrentCaloriesDay(): LiveData<CaloriesDay?>
}