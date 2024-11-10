package dev.nikdekur.classcharts.homework.web

import dev.nikdekur.classcharts.ClassChartsClient
import dev.nikdekur.classcharts.ClassChartsService
import dev.nikdekur.classcharts.date.DateType
import dev.nikdekur.classcharts.homework.Homework
import dev.nikdekur.classcharts.homework.HomeworkService
import dev.nikdekur.classcharts.homework.modal.HomeworkResponse
import dev.nikdekur.classcharts.web.WebService
import dev.nikdekur.classcharts.web.modal.ClassChartsResponse
import dev.nikdekur.ndkore.service.dependencies
import dev.nikdekur.ndkore.service.inject
import kotlinx.datetime.LocalDate

class WebHomeworkService(
    override val app: ClassChartsClient
) : ClassChartsService(), HomeworkService {

    override val dependencies = dependencies {
        +WebService::class
    }

    val webService: WebService by inject()

    fun createHomework(modal: HomeworkResponse.Data): ModalHomework {
        return object : ModalHomework(modal) {
            override suspend fun tick() {
                ticked = !ticked
                tickHomework(this)
            }
        }
    }

    override suspend fun getAllHomeworks(): Collection<Homework> {
        val response = webService.request(
            path = HomeWorkPaths.requestAllHomeWork,
            body = Unit
        )

        val body = response.getBody()
        if (body is ClassChartsResponse.Error) {
            throw IllegalStateException("Failed to get homeworks: ${body.error}")
        }

        val homeworkModals = body as HomeworkResponse

        return homeworkModals.data.map(::createHomework)
    }

    override suspend fun getHomeworks(
        from: LocalDate,
        to: LocalDate,
        displayDate: DateType
    ): Collection<Homework> {

        val displayDate = displayDate.name.lowercase()

        // dates are in ISO-8601 format
        val from = from.toString()
        val to = to.toString()

        val response = webService.request(
            path = HomeWorkPaths.requestHomeWork,
            body = Unit,
            parameters = mapOf(
                "from" to from,
                "to" to to,
                "display_date" to displayDate
            )
        )

        val body = response.getBody()
        if (body is ClassChartsResponse.Error) {
            throw IllegalStateException("Failed to get homeworks: ${body.error}")
        }

        val homeworkModals = body as HomeworkResponse

        return homeworkModals.data.map(::createHomework)
    }


    override suspend fun tickHomework(homework: Homework) {
        webService.request(
            path = HomeWorkPaths.tickHomeWork,
            body = Unit,
            parameters = mapOf(
                "homeworkStatusId" to homework.statusId
            )
        )
    }
}