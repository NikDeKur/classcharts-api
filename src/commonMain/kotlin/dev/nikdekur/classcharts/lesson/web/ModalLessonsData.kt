package dev.nikdekur.classcharts.lesson.web

import dev.nikdekur.classcharts.lesson.LessonsService
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class ModalLessonsData(
    val data: LessonsModal
) : LessonsService.LessonsData {
    val meta = data.meta
    override val startTime = Instant.parse(meta.startTime)
    override val endTime = Instant.parse(meta.endTime)
    override val lessons = data.data.map(::ModalLesson)
    override val timetableDates = data.meta.timetableDates.map(LocalDate::parse)
    override val periods = data.meta.periods.map {
        object : LessonsService.Period {
            override val number = it.number.toInt()
            override val startTime = LocalTime.parse(it.startTime)
            override val endTime = LocalTime.parse(it.endTime)
        }
    }
}
