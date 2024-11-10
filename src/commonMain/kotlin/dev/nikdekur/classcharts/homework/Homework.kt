package dev.nikdekur.classcharts.homework

import dev.nikdekur.ndkore.`interface`.Unique
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.Duration

interface Homework : Unique<Int> {

    val statusId: Int

    val lesson: String
    val subject: String?
    val teacher: String
    val homeworkType: String
    val title: String
    val metaTitle: String
    val description: String

    val issueDate: LocalDate
    val publishTime: LocalTime
    val dueDate: LocalDate

    /**
     * Estimated time to complete the homework.
     *
     * Null if the teacher did not specify the time.
     */
    val completionTime: Duration?


    val state: State
    val ticked: Boolean

    suspend fun tick()

//    val validatedLinks: List<ValidatedLink>
//    val validatedAttachments: List<ValidatedAttachment>

    enum class State {
        TODO,
        NOT_SUBMITTED,
        LATE_SUBMITTED,
        SUBMITTED
        ;
    }
}


inline val Homework.State.isSubmitted: Boolean
    get() = this == Homework.State.SUBMITTED || this == Homework.State.LATE_SUBMITTED