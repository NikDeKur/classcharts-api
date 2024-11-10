package dev.nikdekur.classcharts.web

import dev.nikdekur.classcharts.pupil.PupilData

interface WebService {

    val pupilData: PupilData

    suspend fun <T> request(
        path: Path<T>,
        body: T,
        parameters: Map<String, Any> = emptyMap()
    ): HttpResponse
}

suspend inline fun WebService.request(path: Path<Unit>, parameters: Map<String, Any> = emptyMap()) =
    request(path, Unit, parameters)