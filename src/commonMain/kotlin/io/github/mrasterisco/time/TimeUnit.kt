package io.github.mrasterisco.time

interface TimeUnit {

    val timeIntervalRatio: Double

    fun <OtherUnit : TimeUnit> conversionRate(otherTimeUnit: OtherUnit): Double {
        return timeIntervalRatio / otherTimeUnit.timeIntervalRatio
    }

}