package by.valtorn.caloriescounter.ui

import androidx.lifecycle.*
import by.valtorn.caloriescounter.database.calories.CaloriesDay
import by.valtorn.caloriescounter.repository.CaloriesRepository
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class MainVM : ViewModel() {

    val currentCaloriesDay = Transformations.map(CaloriesRepository.currentCaloriesDay) {
        if (it == null) {
            insert()
        }
        it
    }

    private val mSelectedEating = MutableLiveData<EatingType>()
    val selectedEating: LiveData<EatingType> = mSelectedEating

    fun selectEating(type: EatingType) {
        mSelectedEating.value = type
    }

    private fun insert() {
        viewModelScope.launch {
            CaloriesRepository.insertCaloriesDay(
                CaloriesDay(date = DateTime.now())
            )
        }
    }

    fun setupBreakfast(calories: Int) {
        viewModelScope.launch {
            currentCaloriesDay.value?.let {
                CaloriesRepository.updateCaloriesDay(
                    it.copy(
                        breakfastTime = DateTime.now(),
                        breakfastCalories = calories
                    )
                )
            }
        }
    }

    fun setupLunch(calories: Int) {
        viewModelScope.launch {
            currentCaloriesDay.value?.let {
                CaloriesRepository.updateCaloriesDay(
                    it.copy(
                        lunchTime = DateTime.now(),
                        lunchCalories = calories
                    )
                )
            }
        }
    }

    fun setupDinner(calories: Int) {
        viewModelScope.launch {
            currentCaloriesDay.value?.let {
                CaloriesRepository.updateCaloriesDay(
                    it.copy(
                        dinnerTime = DateTime.now(),
                        dinnerCalories = calories
                    )
                )
            }
        }
    }

    enum class EatingType {
        BREAKFAST,
        LUNCH,
        DINNER
    }
}