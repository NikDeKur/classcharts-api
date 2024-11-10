package dev.nikdekur.classcharts.web

import dev.nikdekur.classcharts.web.modal.ClassChartsResponse
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface Path<T> {

    val bodyType: KType
    val returnType: KType

    val method: HttpMethod
    val contentType: ContentType
    val requireAuth: Boolean

    val path: String
}


inline fun <reified T, reified R : ClassChartsResponse> path(
    method: HttpMethod,
    contentType: ContentType,
    path: String,
    requireAuth: Boolean = true
) = object : Path<T> {

    override val bodyType = typeOf<T>()
    override val returnType = typeOf<R>()

    override val method = method
    override val contentType = contentType
    override val requireAuth = requireAuth

    override val path = path
}