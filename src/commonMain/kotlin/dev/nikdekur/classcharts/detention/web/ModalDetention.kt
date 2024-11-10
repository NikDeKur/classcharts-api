package dev.nikdekur.classcharts.detention.web

import dev.nikdekur.classcharts.detention.Detention
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes

@Serializable
data class ModalDetention(
    val modal: DetentionsResponse.Data
) : Detention {

    override val id = modal.id
    override val name = modal.detentionType.name

    override val attended = Detention.AttendStatus.fromString(modal.attended)
    override val time = getDateTime(modal.date, modal.time)
    override val duration = modal.length.minutes
    override val location = modal.location
    override val notes = modal.notes
    override val pupil = ModalPupil(
        modal.pupil.id,
        modal.pupil.firstName,
        modal.pupil.lastName,
        ModalPupil.ModalSchool(
            modal.pupil.school.optNotesNames,
            modal.pupil.school.optNotesComments,
            modal.pupil.school.optNotesCommentsPupils
        )
    )
    override val lesson = modal.lesson?.let {
        ModalLesson(
            it.id,
            it.name,
            ModalLesson.ModalSubject(
                it.subject.id,
                it.subject.name
            )
        )
    }
    override val reason = modal.lessonPupilBehaviour.reason
    override val teacher = Teacher(
        modal.teacher.id,
        modal.teacher.firstName,
        modal.teacher.lastName,
        modal.teacher.title
    )


    @Serializable
    data class ModalPupil(
        override val id: Int,
        override val firstName: String,
        override val lastName: String,
        override val school: Detention.Pupil.School
    ) : Detention.Pupil {

        @Serializable
        data class ModalSchool(
            override val optNotesNames: String,
            override val optNotesComments: String,
            override val optNotesCommentsPupils: String
        ) : Detention.Pupil.School
    }


    @Serializable
    data class ModalLesson(
        override val id: Int,
        override val name: String,
        override val subject: Detention.Lesson.Subject
    ) : Detention.Lesson {

        @Serializable
        data class ModalSubject(
            override val id: Int,
            override val name: String
        ) : Detention.Lesson.Subject
    }

    @Serializable
    data class Teacher(
        override val id: Int,
        override val firstName: String,
        override val lastName: String,
        override val title: String
    ) : Detention.Teacher


    companion object {
        val timeFormat = LocalTime.Format {
            hour()
            char(':')
            minute()
        }

        fun getDateTime(date: String, time: String): LocalDateTime {
            val date = Instant.parse(date).toLocalDateTime(TimeZone.UTC).date
            return date.atTime(LocalTime.parse(time, timeFormat))
        }
    }
}