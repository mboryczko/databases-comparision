package pl.michalboryczko.exercise.extensions

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

val SIMPLE_DATE_FORMAT: String = "yyyy-MM-dd"

fun Date.convertDateToSimpleString(): String{
    val spf = SimpleDateFormat(SIMPLE_DATE_FORMAT)
    val date = spf.format(this)
    return date
}

fun Calendar.convertCalendarToSimpleString(): String{
    val spf = SimpleDateFormat(SIMPLE_DATE_FORMAT)
    val date = spf.format(this.time)
    return date
}