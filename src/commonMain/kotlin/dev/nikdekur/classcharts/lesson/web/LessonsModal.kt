package dev.nikdekur.classcharts.lesson.web

import dev.nikdekur.classcharts.web.modal.ClassChartsResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonsModal(
    val data: List<LessonData>,
    val meta: LessonMeta
) : ClassChartsResponse() {

    @Serializable
    data class LessonData(
        @SerialName("teacher_name")
        val teacherName: String,
        @SerialName("lesson_id")
        val lessonId: Int,
        @SerialName("lesson_name")
        val lessonName: String,
        @SerialName("subject_name")
        val subjectName: String,
        @SerialName("is_alternative_lesson")
        val isAlternativeLesson: Boolean,
        @SerialName("period_name")
        val periodName: String,
        @SerialName("period_number")
        val periodNumber: String,
        @SerialName("room_name")
        val roomName: String,
        @SerialName("date")
        val date: String,
        @SerialName("start_time")
        val startTime: String,
        @SerialName("end_time")
        val endTime: String,
        @SerialName("key")
        val key: Int,
        @SerialName("note_abstract")
        val noteAbstract: String,
        @SerialName("note")
        val note: String,
        @SerialName("pupil_note_abstract")
        val pupilNoteAbstract: String,
        @SerialName("pupil_note")
        val pupilNote: String,
        @SerialName("pupil_note_raw")
        val pupilNoteRaw: String
    )

    @Serializable
    data class LessonMeta(
        val dates: List<String>,
        @SerialName("timetable_dates")
        val timetableDates: List<String>,
        @SerialName("periods")
        val periods: List<Period>,
        @SerialName("start_time")
        val startTime: String,
        @SerialName("end_time")
        val endTime: String
    ) {

        @Serializable
        data class Period(
            val number: String,
            @SerialName("start_time")
            val startTime: String,
            @SerialName("end_time")
            val endTime: String
        )
    }
}