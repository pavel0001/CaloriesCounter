package by.valtorn.caloriescounter.utils.date

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


private val caloriesDateTimeFormat = DateTimeFormat.forPattern("hh:mm a")


fun DateTime.stringForCalories(): String = caloriesDateTimeFormat.print(this)