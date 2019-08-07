package io.github.mrasterisco.time

val Number.days: Interval<Day>
    get() = Interval(this, ::Day)

val Number.hours: Interval<Hour>
    get() = Interval(this, ::Hour)

val Number.minutes: Interval<Minute>
    get() = Interval(this, ::Minute)

val Number.seconds: Interval<Second>
    get() = Interval(this, ::Second)

val Number.milliseconds: Interval<Millisecond>
    get() = Interval(this, ::Millisecond)

val Number.microseconds: Interval<Microsecond>
    get() = Interval(this, ::Microsecond)

val Number.nanoseconds: Interval<Nanosecond>
    get() = Interval(this, ::Nanosecond)