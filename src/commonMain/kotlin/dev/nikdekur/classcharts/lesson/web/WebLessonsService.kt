package dev.nikdekur.classcharts.lesson.web

import dev.nikdekur.classcharts.ClassChartsClient
import dev.nikdekur.classcharts.ClassChartsService
import dev.nikdekur.classcharts.lesson.LessonsService
import dev.nikdekur.classcharts.lesson.LessonsService.LessonsData
import dev.nikdekur.classcharts.web.ContentType
import dev.nikdekur.classcharts.web.HttpMethod
import dev.nikdekur.classcharts.web.WebService
import dev.nikdekur.classcharts.web.modal.ClassChartsResponse
import dev.nikdekur.classcharts.web.path
import dev.nikdekur.ndkore.service.dependencies
import dev.nikdekur.ndkore.service.inject
import kotlinx.datetime.LocalDate

class WebLessonsService(
    override val app: ClassChartsClient
) : ClassChartsService(), LessonsService {

    override val dependencies = dependencies {
        +WebService::class
    }

    val webService: WebService by app.inject()

    val listLessonsPath = path<Unit, LessonsModal>(
        HttpMethod.GET,
        ContentType.Application.Json,
        "timetable/{studentId}?date={date}"
    )

    override suspend fun getLessons(date: LocalDate): LessonsData {
        val response = webService.request(
            path = listLessonsPath,
            body = Unit,
            parameters = mapOf(
                "date" to date.toString(),
            )
        )

        val body = response.getBody()
        if (body is ClassChartsResponse.Error)
            throw IllegalStateException("Failed to get lessons: ${body.error}")

        body as LessonsModal

        return ModalLessonsData(body)
    }
}