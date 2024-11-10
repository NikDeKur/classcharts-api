package dev.nikdekur.classcharts.lesson

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

fun interface LessonsService {

    suspend fun getLessons(date: LocalDate): LessonsData

    interface LessonsData {
        /**
         * The start time of the day lessons request is for.
         */
        val startTime: Instant

        /**
         * The end time of the day lessons request is for.
         */
        val endTime: Instant

        val lessons: Collection<Lesson>

        /**
         * The dates that lessons are available for this week.
         *
         * All dates returned are guaranteed to have lessons available.
         */
        val timetableDates: Collection<LocalDate>

        val periods: Collection<Period>

    }

    interface Period {
        val number: Int
        val startTime: LocalTime
        val endTime: LocalTime
    }
}