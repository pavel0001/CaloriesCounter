package by.valtorn.caloriescounter.database.datetime

import androidx.room.TypeConverter
import org.joda.time.DateTime

object DateTimeConverter {
    @TypeConverter
    @JvmStatic
    fun toDateTime(fromMillis: Long?): DateTime? = if (fromMillis == null) null else DateTime(fromMillis)

    @TypeConverter
    @JvmStatic
    fun toMillis(fromDateTime: DateTime?): Long? = fromDateTime?.millis
}