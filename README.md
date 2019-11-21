# Time

This is a Kotlin multiplatform implementation based upon the [Time](https://github.com/kizitonwose/Time) library for Android.
The library is made for you if you have ever written something like this: 

```kotlin
val duration = 10 * 1000
```

to represent a duration of 10 seconds(in milliseconds) because most methods in Kotlin/Java take duration parameters in milliseconds.

## Usage

### Showcase

```kotlin
val tenSeconds = 10.seconds
val fiveMinutes = 5.minutes
val twoHours = 2.hours
val threeDays = 3.days
val tenMinutesFromNow = Calendar.getInstance() + 10.minutes
val tenSecondsInMilliseconds = 10.seconds.inMilliseconds
```

### Basics

The main advantage of the library is that all time units are *strongly-typed*. So, for example:

```kotlin
val tenMinutes = 10.minutes
```

In the example above,  `tenMinutes` will be of type `Interval<Minute>`. There are seven time units available, from nanoseconds to days:

```kotlin
val tenNanoseconds = 10.nanoseconds 
// type is Interval<Nanosecond>
```
```kotlin
val tenMicroseconds = 10.microseconds 
// type is Interval<Microsecond>
```
```kotlin
val tenMilliseconds = 10.milliseconds 
// type is Interval<Millisecond>
```
```kotlin
val tenSeconds = 10.seconds 
// type is Interval<Second>
```
```kotlin
val tenMinutes = 10.minutes 
// type is Interval<Minute>
```
```kotlin
val tenHours = 10.hours 
// type is Interval<Hour>
```
```kotlin
val tenDays = 10.days 
// type is Interval<Day>
```

### Operations

You can perform all basic arithmetic operations on time intervals, even of different units:

```kotlin
val duration = 10.minutes + 15.seconds - 3.minutes + 2.hours // Interval<Minute>
val doubled = duration * 2

val seconds = 10.seconds + 3.minutes // Interval<Second>
```

You can also use these operations with the `Calendar` class:

```kotlin
val twoHoursLater = Calendar.getInstance() + 2.hours
```

### Conversions

Time intervals are easily convertible:

```kotlin
val twoMinutesInSeconds = 2.minutes.inSeconds // Interval<Second>
val fourDaysInHours = 4.days.inHours // Interval<Hour>
```

You can also use the `converted(TimeUnit)` method, although you would rarely need to:

```kotlin
val tenMinutesInSeconds: Interval<Second> = 10.minutes.converted(Second())
```

### Comparison

You can compare different time units as well:

```kotlin
50.seconds < 2.hours // true
120.minutes == 2.hours // true
100.milliseconds > 2.seconds // false
48.hours in 2.days // true
```

### Creating your own time units

If, for some reason, you need to create your own time unit, that's super easy to do:

```kotlin
class Week : TimeUnit {
    // number of seconds in one week
    override val timeIntervalRatio = 604800.0
}
```

Now you can use it like any other time unit:

```kotlin
val fiveWeeks = Interval<Week>(5)
```

For the sake of convenience, don't forget to write those handy extensions:

```kotlin
class Week : TimeUnit {
    override val timeIntervalRatio = 604800.0
}

val Number.weeks: Interval<Week>
    get() = Interval(this)

val Interval<TimeUnit>.inWeeks: Interval<Week>
    get() = converted(Week())
```
Now you can write:

```kotlin
val fiveWeeks = 5.weeks // Interval<Week>
```
You can also easily convert to weeks:

```kotlin
val valueInWeeks = 14.days.inWeeks // Interval<Week>
```

### Conversion safety everywhere

For time-related methods in other third-party libraries in your project, if such methods are frequently used, it's best to write extention functions that let you use the time units in this libary in those methods. This is mostly just one line of code. 

If such methods aren't frequently used, you can still benefit from the conversion safety that comes with this library.

An example method in a third-party library that does something after a delay period in milliseconds:

```kotlin
class Person {
    fun doSomething(delayMillis: Long) {
        // method body
    }
}
```

To call the method above with a value of 5 minutes, one would usually write:

```kotlin
val person = Person()
person.doSomething(5 * 60 * 1000)
```

The above line can be written in a safer and clearer way using this library:

```kotlin
val person = Person()
person.doSomething(5.minutes.inMilliseconds.longValue)
```

If the method is frequently used, you can write an extension function:

```kotlin
fun Person.doSomething(delay: Interval<TimeUnit>) {
    doSomething(delay.inMilliseconds.longValue)
}
```
Now you can write:

```kotlin
val person = Person()
person.doSomething(5.minutes)
```

## Changes from the Android library

Due to the current development status of the Kotlin multiplatform project, some APIs have been changed.

### converted()
In the original library, the `converted` method uses Java reflection to instantiate 
a new class of the required `TimeUnit` implementation. 
Since in Kotlin multiplatform, the reflection API is still in early stages (and not implemented on some platforms), 
I have added a required parameter that points to an instance of the to the expected `TimeUnit` implementation.

### Android extensions
The content of the Android extensions provided by the original library have not been ported to this implementation.

## Installation

Add the repository to your `build.gradle`:

```groovy
allprojects {
 repositories {
    maven { url "https://dl.bintray.com/mrasterisco/Maven" }
 }
}
```

Add the dependency to your `build.gradle`:

```groovy
dependencies {
    implementation "io.github.mrasterisco:Time-<target>:<version>"
}
```

Since version 1.5.0, targets follow the same naming convention used by KotlinX Serialization and other KotlinX libraries. See below for further details.

#### Common
To include the library into a Kotlin common module, use:

```groovy
dependencies {
    implementation "io.github.mrasterisco:Time-common:<version>"
}
```

#### JVM
To include the library into a JVM module (including Android), use:

```groovy
dependencies {
    implementation "io.github.mrasterisco:Time:<version>"
}
```

#### Native
To include the library into a Native module, use:

```groovy
dependencies {
    implementation "io.github.mrasterisco:Time-native:<version>"
}
```

If you're including the library into a target that builds for multiple architectures, make sure to put the following into your `settings.gradle` file.

```kotlin
enableFeaturePreview("GRADLE_METADATA")
```

#### JS
To include the library into your JavaScript module, use:

```groovy
dependencies {
    implementation "io.github.mrasterisco:Time-js:<version>"
}
```

### Compatibility

The library uses only Kotlin common code and does not provide explicit implementation for any platform, hence it should work out-of-the-box everywhere. See the table below for further details:

|                      	|     iOS     	|    macOS    	|     JVM     	|                nodeJS                	|  browserJS  	| Windows 	| Linux 	|
|:--------------------:	|:-----------:	|:-----------:	|:-----------:	|:------------------------------------:	|:-----------:	|:-------:	|:-----:	|
|  Built using Gradle  	|     YES     	|     YES     	|     YES     	|                  YES                 	|     YES     	|    NO   	|   NO  	|
|    Unit Tests ran    	| YES, passed 	| YES, passed 	| YES, passed 	| YES, failed due to bad configuration 	| YES, passed 	|    NO   	|   NO  	|
| Published to Bintray 	|  YES, -native  	| YES, -macos 	|  YES, -jvm  	|               YES, -js               	|   YES, -js  	|    NO   	|   NO  	|
|   Used in a Project  	|     YES     	|      NO     	|     YES     	|                  NO                  	|      NO     	|    NO   	|   NO  	|

If you start using this library in a project different than iOS and Android, make a PR to update this file, so that others know that it has been implemented successfully somewhere.

## Contributing
The goal is for the library to be used wherever possible. If there are extension functions or features you think the library should have, feel free to add them and send a pull request or open an issue.

## Inspiration
This library is heavily based on the [Time](https://github.com/kizitonwose/Time) for Android with a few changes in order to make it work in the Kotlin multiplatform environment.
Time was inspired by a Swift library of the same name - [Time](https://github.com/dreymonde/Time).

## License
Time is distributed under the MIT license. [See LICENSE](https://github.com/MrAsterisco/Time/blob/master/LICENSE) for details.
