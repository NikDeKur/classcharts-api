package dev.nikdekur.classcharts.web.modal

import kotlinx.serialization.Serializable

@Serializable
abstract class ClassChartsResponse {

    @Serializable
    data class Error(
        val expired: Int,
        val error: String,
    ) : ClassChartsResponse()
}