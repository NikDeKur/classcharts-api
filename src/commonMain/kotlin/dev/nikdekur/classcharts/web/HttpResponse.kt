package dev.nikdekur.classcharts.web

import dev.nikdekur.classcharts.web.modal.ClassChartsResponse
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

interface HttpResponse {
    val statusCode: Int

    suspend fun getBody(): ClassChartsResponse
    suspend fun <T> getBody(type: TypeInfo): T
}

suspend inline fun <reified T> HttpResponse.getBody(): T {
    return getBody(typeInfo<T>())
}