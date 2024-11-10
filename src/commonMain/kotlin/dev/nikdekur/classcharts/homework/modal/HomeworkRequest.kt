package dev.nikdekur.classcharts.homework.modal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeworkRequest(
    @SerialName("display_date")
    val displayDate: String,

    @SerialName("from")
    val from: String,

    @SerialName("to")
    val to: String,
)
