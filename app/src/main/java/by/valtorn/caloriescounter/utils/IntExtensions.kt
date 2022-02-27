package by.valtorn.caloriescounter.utils


fun Int?.plusNullable(otherInt: Int?) = (this ?: 0).plus(otherInt ?: 0)
fun Int?.orZero() = this ?: 0
fun Int.positiveOrNul() = if (this >= 0) this else 0