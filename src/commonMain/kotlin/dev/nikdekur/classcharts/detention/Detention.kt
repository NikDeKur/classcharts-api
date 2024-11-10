package dev.nikdekur.classcharts.detention

import dev.nikdekur.ndkore.`interface`.Unique
import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration

interface Detention : Unique<Int> {

    val name: String

    val attended: AttendStatus

    val time: LocalDateTime

    val duration: Duration

    val location: String

    val notes: String?

    val pupil: Pupil
    val lesson: Lesson?

    val reason: String

    val teacher: Teacher




    enum class AttendStatus {
        ATTENDED,
        NOT_ATTENDED,
        UPSCALED
        ;


        companion object {
            fun fromString(string: String): AttendStatus {
                return when (string) {
                    "yes" -> ATTENDED
                    "not" -> NOT_ATTENDED
                    "upscaled" -> UPSCALED
                    else -> throw IllegalArgumentException("Unknown attend status: $string")
                }
            }
        }
    }


    interface Pupil : Unique<Int> {
        val firstName: String
        val lastName: String
        val school: School

        interface School {
            val optNotesNames: String
            val optNotesComments: String
            val optNotesCommentsPupils: String
        }
    }


    interface Lesson : Unique<Int> {
        val name: String
        val subject: Subject

        interface Subject : Unique<Int> {
            val name: String
        }
    }


    interface Teacher : Unique<Int> {
        val firstName: String
        val lastName: String
        val title: String
    }
}