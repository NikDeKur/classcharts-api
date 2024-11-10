package dev.nikdekur.classcharts.detention.web

import dev.nikdekur.classcharts.web.ContentType
import dev.nikdekur.classcharts.web.HttpMethod
import dev.nikdekur.classcharts.web.path

object DetentionsPaths {

    val requestAllDetentions = path<Unit, DetentionsResponse>(
        HttpMethod.GET,
        ContentType.Application.Json,
        "detentions/{studentId}"
    )
}