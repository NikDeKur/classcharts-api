package dev.nikdekur.classcharts.homework.web

import dev.nikdekur.classcharts.homework.Homework
import dev.nikdekur.classcharts.homework.modal.HomeworkResponse
import dev.nikdekur.ndkore.ext.toBooleanSmart
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Serializable
open class ModalHomework(
    val modal: HomeworkResponse.Data
) : Homework {


    val status = modal.status

    override val id = modal.id
    override val statusId = status.id


    override val lesson = modal.lesson
    override val subject = modal.subject
    override val teacher = modal.teacher
    override val homeworkType = modal.homeworkType
    override val title = modal.title
    override val metaTitle = modal.metaTitle
    override val description = modal.description
    override val issueDate = LocalDate.parse(modal.issueDate)
    override val publishTime = LocalTime.parse(modal.publishTime)
    override val dueDate = LocalDate.parse(modal.dueDate)
    override val completionTime = run {
        val unit = decodeUnit(modal.completionTimeUnit)
            ?: return@run null
        val value = modal.completionTimeValue.toLongOrNull()
            ?: return@run null
        value.toDuration(unit)
    }

    override val state = decodeHomeWorkState(status.state)

    override var ticked = status.ticked.toBooleanSmart()

    override suspend fun tick() {
        ticked = !ticked
    }


    override fun toString(): String {
        return "ModalHomework(id=$id, lesson='$lesson', subject=$subject, teacher='$teacher', homeworkType='$homeworkType', title='$title', metaTitle='$metaTitle', description='$description', issueDate=$issueDate, publishTime=$publishTime, dueDate=$dueDate, completionTime=$completionTime, state=$state, ticked=$ticked)"
    }

    companion object {
        fun decodeUnit(unit: String): DurationUnit? {
            return when {
                unit.contains("second", true) -> DurationUnit.SECONDS
                unit.contains("minute", true) -> DurationUnit.MINUTES
                unit.contains("hour", true) -> DurationUnit.HOURS
                unit.contains("day", true) -> DurationUnit.DAYS
                else -> null
            }
        }


        fun decodeHomeWorkState(value: String?): Homework.State {
            return when (value?.lowercase()) {
                null -> Homework.State.TODO
                "not_completed" -> Homework.State.NOT_SUBMITTED
                "late" -> Homework.State.LATE_SUBMITTED
                "completed" -> Homework.State.SUBMITTED
                else -> error("Unknown state: $value")
            }
        }
    }
}
