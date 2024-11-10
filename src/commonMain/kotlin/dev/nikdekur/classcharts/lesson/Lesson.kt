@file:Suppress("NOTHING_TO_INLINE")

package dev.nikdekur.classcharts.lesson

import dev.nikdekur.ndkore.`interface`.Unique
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion
import kotlin.time.Duration.Companion.ZERO

interface Lesson : Unique<Int> {

    val name: String
    val subject: String
    val teacherName: String
    val isAlternativeLesson: Boolean
    val periodName: String
    val periodNumber: String
    val roomName: String

    val date: LocalDate

    val startTime: Instant
    val endTime: Instant

    val key: Int
}

/**
 * Check if the lesson is active at the given time
 *
 * @param time The time to check
 * @return True if the lesson is active at the given time
 */
fun Lesson.isActiveAt(time: Instant): Boolean {
    return time in startTime..endTime
}

inline fun Lesson.isActiveNow(
    clock: Clock = Clock.System
): Boolean {
    return isActiveAt(clock.now())
}

/**
 * Get time left until the lesson ends
 *
 * If the lesson is already over or hasn't started yet, the result will be [Duration.ZERO]
 *
 * @return [Duration] until the lesson ends
 * @see isActiveNow
 */
inline fun Lesson.getTimeLeft(
    clock: Clock = Clock.System
): Duration {
    val nowInstant = clock.now()
    val now = nowInstant
    return if (isActiveAt(now)) {
        endTime - nowInstant
    } else {
        ZERO
    }
}



