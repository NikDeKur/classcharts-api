package dev.nikdekur.classcharts.lesson.web

import dev.nikdekur.classcharts.lesson.Lesson
import dev.nikdekur.classcharts.lesson.web.LessonsModal.LessonData
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ModalLesson(
    val modal: LessonData,
) : Lesson {

    override val id = modal.lessonId
    override val key = modal.key

    override val teacherName = modal.teacherName
    override val name = modal.lessonName
    override val subject = modal.subjectName
    override val isAlternativeLesson = modal.isAlternativeLesson
    override val periodName = modal.periodName
    override val periodNumber = modal.periodNumber
    override val roomName = modal.roomName
    override val date = LocalDate.parse(modal.date)
    override val startTime = Instant.parse(modal.startTime)
    override val endTime = Instant.parse(modal.endTime)

    override fun toString(): String {
        return "ModalLesson(teacherName='$teacherName', lessonName='$name', subjectName='$subject', isAlternativeLesson=$isAlternativeLesson, periodName='$periodName', periodNumber='$periodNumber', roomName='$roomName', date=$date, startTime=$startTime, endTime=$endTime, key=$key)"
    }
}
