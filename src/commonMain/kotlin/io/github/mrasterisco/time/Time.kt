@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.github.mrasterisco.time

import kotlin.math.round

class Interval<out T: TimeUnit>(value: Number, factory: () -> T) {

    val unit: T = factory()

    val value = value.toDouble()

    val longValue = round(this.value).toLong()

    val inDays: Interval<Day>
        get() = converted(Day())

    val inHours: Interval<Hour>
        get() = converted(Hour())

    val inMinutes: Interval<Minute>
        get() = converted(Minute())

    val inSeconds: Interval<Second>
        get() = converted(Second())

    val inMilliseconds: Interval<Millisecond>
        get() = converted(Millisecond())

    val inMicroseconds: Interval<Microsecond>
        get() = converted(Microsecond())

    val inNanoseconds: Interval<Nanosecond>
        get() = converted(Nanosecond())

    inline fun <reified OtherUnit : TimeUnit> converted(instance: OtherUnit): Interval<OtherUnit> =
        Interval(value * unit.conversionRate(instance)) { instance }

    operator fun plus(other: Interval<TimeUnit>): Interval<T> {
        val newValue = value + other.value * other.unit.conversionRate(unit)
        return Interval(newValue) { unit }
    }

    operator fun minus(other: Interval<TimeUnit>): Interval<T> {
        val newValue = value - other.value * other.unit.conversionRate(unit)
        return Interval(newValue) { unit }
    }

    operator fun times(other: Number): Interval<T> {
        return Interval(value * other.toDouble()) { unit }
    }

    operator fun div(other: Number): Interval<T> {
        return Interval(value / other.toDouble()) { unit }
    }

    operator fun inc() = Interval(value + 1) { unit }

    operator fun dec() = Interval(value - 1) { unit }

    operator fun compareTo(other: Interval<TimeUnit>) = inMilliseconds.value.compareTo(other.inMilliseconds.value)

    operator fun contains(other: Interval<TimeUnit>) = inMilliseconds.value >= other.inMilliseconds.value

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is Interval<TimeUnit>) return false
        return compareTo(other) == 0
    }

    override fun hashCode() = inMilliseconds.value.hashCode()

    override fun toString(): String {
        val unitString = unit::class.simpleName?.toLowerCase() ?: ""
        val isWhole = value % 1 == 0.0
        return (if (isWhole) longValue.toString() else value.toString())
            .plus(" ")
            .plus(if (value == 1.0) unitString else unitString.plus("s"))
    }

}