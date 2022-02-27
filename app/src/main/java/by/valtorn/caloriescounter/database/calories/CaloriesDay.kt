package by.valtorn.caloriescounter.database.calories

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.valtorn.caloriescounter.database.datetime.DateTimeConverter
import by.valtorn.caloriescounter.utils.plusNullable
import org.joda.time.DateTime

@Entity
@TypeConverters(DateTimeConverter::class)
data class CaloriesDay(
    @PrimaryKey(autoGenerate = true)
    val dayId: Long = 0,
    val date: DateTime,
    val breakfastCalories: Int? = null,
    val breakfastTime: DateTime? = null,
    val lunchCalories: Int? = null,
    val lunchTime: DateTime? = null,
    val dinnerCalories: Int? = null,
    val dinnerTime: DateTime? = null
) {
    fun getEatingCalories() = breakfastCalories.plusNullable(lunchCalories).plusNullable(dinnerCalories)
}