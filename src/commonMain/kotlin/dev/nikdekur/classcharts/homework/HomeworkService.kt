package dev.nikdekur.classcharts.homework

import dev.nikdekur.classcharts.date.DateType
import kotlinx.datetime.LocalDate

interface HomeworkService {

    suspend fun getAllHomeworks(): Collection<Homework>

    /**
     * Get homeworks between the given dates.
     *
     * @param from The start date.
     * @param to The end date.
     * @param displayDate The date to display. If [DateType.ISSUE_DATE] is selected,
     * the homeworks which were issued on the given dates will be returned.
     * If [DateType.DUE_DATE] is selected, the homeworks which are due on the given dates will be returned.
     * @return The list of homeworks.
     */
    suspend fun getHomeworks(
        from: LocalDate,
        to: LocalDate,
        displayDate: DateType = DateType.ISSUE_DATE,
    ): Collection<Homework>


    /**
     * Tick the homework.
     *
     * Tick actually means to mark the homework as completed.
     *
     * If the homework is already ticked as completed, the method will revert the tick (mark as not completed).
     *
     * @param homework The homework to tick.
     * @see Homework.ticked
     */
    suspend fun tickHomework(homework: Homework)
}