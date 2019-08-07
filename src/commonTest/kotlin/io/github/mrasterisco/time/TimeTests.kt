package io.github.mrasterisco.time

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TimeTest {

    @Test
    fun timeComparisonsWorkAsExpected() {
        assertTrue(5.minutes > 120.seconds)
        assertTrue(2.days < 48.5.hours)
        assertTrue(1000.microseconds > 2000.nanoseconds)
        assertTrue(60.seconds.equals(60000.milliseconds))
    }

    @Test
    fun timeConversionsWorkAsExpected() {
        val twentyFourHours = 24.hours

        val valueInDays = twentyFourHours.inSeconds.inMinutes.inNanoseconds
            .inMicroseconds.inHours.inMilliseconds.inDays

        assertEquals(1.0, valueInDays.value)
    }

    @Test
    fun basicTimeOperatorsWorkAsExpected() {
        val sixtySecs = 60.seconds

        var newValue = sixtySecs + 2.minutes
        newValue -= 20.seconds
        newValue += 10.seconds

        assertEquals(170.seconds, newValue)
    }

    @Test
    fun timeInOperatorWorksAsExpected() {
        assertTrue(60.minutes in 4.hours)
        assertFalse(2.days in 24.hours)
        assertTrue(120.seconds in 2.minutes)
    }

    @Test
    fun timeOperatorsMultiplicationAndDivisionWorkAsExpected() {
        val sixtySecs = 60.seconds

        val multiplied = sixtySecs * 2
        val divided = sixtySecs / 2

        assertEquals(multiplied, 120.seconds)
        assertEquals(divided, 30.seconds)
    }

    @Test
    fun timeOperatorsIncrementAndDecrementWorkAsExpected() {
        var days = 2.days

        days++
        assertEquals(days, 3.days)

        days--
        assertEquals(days, 2.days)
    }

    @Test
    fun customTimeUnitsWorkAsExpected() {
        val twoWeeks = 2.weeks
        val fourteenDays = 14.days

        assertTrue(twoWeeks.equals(fourteenDays))
        assertEquals(336.hours.inWeeks, twoWeeks)
    }
}


// Custom time unit.
class Week : TimeUnit {
    override val timeIntervalRatio = 604800.0
}

val Number.weeks: Interval<Week>
    get() = Interval(this.toDouble(), ::Week)

val Interval<TimeUnit>.inWeeks: Interval<Week>
    get() = converted(Week())