package pl.michalboryczko.exercise

import org.junit.Test

import org.junit.Assert.*
import pl.michalboryczko.exercise.BaseTest
import pl.michalboryczko.exercise.extensions.convertCalendarToSimpleString
import pl.michalboryczko.exercise.extensions.convertDateToSimpleString
import java.util.*


class DateTests: BaseTest() {

    /*
        0 based value in month set
     */


    @Test
    fun testCalendarToStringConversion(){
        val calendar = Calendar.getInstance()
        calendar.set(2019, 11, 3)
        val output = calendar.convertCalendarToSimpleString()
        assertEquals("2019-11-03", output)
    }

    @Test
    fun testCalendarToStringJanuaryConversion(){
        val calendar = Calendar.getInstance()
        calendar.set(2019, 0, 3)
        val output = calendar.convertCalendarToSimpleString()
        assertEquals("2019-01-03", output)
    }

    @Test
    fun testCalendarToStringDecemberConversion(){
        val calendar = Calendar.getInstance()
        calendar.set(2019, 11, 3)
        val output = calendar.convertCalendarToSimpleString()
        assertEquals("2019-12-03", output)
    }

    @Test
    fun testDateToStringConversion(){
        val date = Date(2019, 11, 3)
        val output = date.convertDateToSimpleString()
        assertEquals("2019-12-03", output)
    }
}
