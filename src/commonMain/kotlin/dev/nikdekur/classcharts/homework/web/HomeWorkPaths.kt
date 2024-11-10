package dev.nikdekur.classcharts.homework.web

import dev.nikdekur.classcharts.homework.modal.HomeworkResponse
import dev.nikdekur.classcharts.web.ContentType
import dev.nikdekur.classcharts.web.HttpMethod
import dev.nikdekur.classcharts.web.modal.ClassChartsResponse
import dev.nikdekur.classcharts.web.path

object HomeWorkPaths {

    val requestAllHomeWork = path<Unit, HomeworkResponse>(
        HttpMethod.GET,
        ContentType.Application.Json,
        "homeworks/{studentId}"
    )

    val requestHomeWork = path<Unit, HomeworkResponse>(
        HttpMethod.GET,
        ContentType.Application.Json,
        "homeworks/{studentId}?from={from}&to={to}&displayDate={display_date}"
    )


    val tickHomeWork = path<Unit, ClassChartsResponse>(
        HttpMethod.POST,
        ContentType.Application.Json,
        "homeworkticked/{homeworkStatusId}?studentId={studentId}"
    )
}