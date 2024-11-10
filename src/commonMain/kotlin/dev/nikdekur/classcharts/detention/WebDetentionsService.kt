package dev.nikdekur.classcharts.detention

import dev.nikdekur.classcharts.ClassChartsClient
import dev.nikdekur.classcharts.ClassChartsService
import dev.nikdekur.classcharts.detention.web.DetentionsPaths
import dev.nikdekur.classcharts.detention.web.DetentionsResponse
import dev.nikdekur.classcharts.detention.web.ModalDetention
import dev.nikdekur.classcharts.web.WebService
import dev.nikdekur.classcharts.web.modal.ClassChartsResponse
import dev.nikdekur.classcharts.web.request
import dev.nikdekur.ndkore.service.dependencies
import dev.nikdekur.ndkore.service.inject

class WebDetentionsService(
    override val app: ClassChartsClient
) : ClassChartsService(), DetentionsService {

    override val dependencies = dependencies {
        +WebService::class
    }

    val webService: WebService by inject()

    fun createDetention(modal: DetentionsResponse.Data): Detention {
        return ModalDetention(modal)
    }

    override suspend fun getDetentions(): Collection<Detention> {
        val response = webService.request(
            path = DetentionsPaths.requestAllDetentions
        )

        val body = response.getBody()
        if (body is ClassChartsResponse.Error) {
            throw IllegalStateException("Failed to get detentions: ${body.error}")
        }

        val detentions = body as DetentionsResponse

        return detentions.data.map(::createDetention)
    }


}