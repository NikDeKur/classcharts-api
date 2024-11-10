package dev.nikdekur.classcharts.detention.web

import dev.nikdekur.classcharts.web.modal.ClassChartsResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetentionsResponse(
    val success: Int,
    val data: List<Data>,
    val meta: Meta
) : ClassChartsResponse() {

    @Serializable
    data class Data(
        val id: Int,
        val attended: String,
        val date: String,
        val length: Int,
        val location: String,
        val notes: String?,
        val time: String,
        val pupil: Pupil,
        val lesson: Lesson?,
        @SerialName("lesson_pupil_behaviour")
        val lessonPupilBehaviour: LessonPupilBehaviour,
        val teacher: Teacher,
        @SerialName("detention_type")
        val detentionType: DetentionType
    )

    @Serializable
    data class Pupil(
        val id: Int,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        val school: School
    )

    @Serializable
    data class School(
        @SerialName("opt_notes_names")
        val optNotesNames: String,
        @SerialName("opt_notes_comments")
        val optNotesComments: String,
        @SerialName("opt_notes_comments_pupils")
        val optNotesCommentsPupils: String
    )

    @Serializable
    data class Lesson(
        val id: Int,
        val name: String,
        val subject: Subject
    )

    @Serializable
    data class Subject(
        val id: Int,
        val name: String
    )

    @Serializable
    data class LessonPupilBehaviour(
        val reason: String
    )

    @Serializable
    data class Teacher(
        val id: Int,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        val title: String
    )

    @Serializable
    data class DetentionType(
        val name: String
    )

    @Serializable
    data class Meta(
        @SerialName("detention_alias_plural")
        val detentionAliasPlural: String
    )
}