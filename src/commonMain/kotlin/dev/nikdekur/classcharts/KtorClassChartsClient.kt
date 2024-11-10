package dev.nikdekur.classcharts

import dev.nikdekur.classcharts.detention.DetentionsService
import dev.nikdekur.classcharts.detention.WebDetentionsService
import dev.nikdekur.classcharts.homework.HomeworkService
import dev.nikdekur.classcharts.homework.web.WebHomeworkService
import dev.nikdekur.classcharts.lesson.LessonsService
import dev.nikdekur.classcharts.lesson.web.WebLessonsService
import dev.nikdekur.classcharts.pupil.PupilData
import dev.nikdekur.classcharts.web.KtorWebService
import dev.nikdekur.classcharts.web.WebService
import dev.nikdekur.ndkore.service.get
import dev.nikdekur.ndkore.service.manager.RuntimeServicesManager
import dev.nikdekur.ndkore.service.manager.ServicesManager
import dev.nikdekur.ndkore.service.manager.throwOnError
import dev.nikdekur.ornament.AbstractApplication
import dev.nikdekur.ornament.environment.Environment
import kotlin.apply

open class KtorClassChartsClient(
    override val environment: Environment,
    val data: ConnectData
) : AbstractApplication(), ClassChartsClient {

    override val pupilData: PupilData
        get() = get<WebService>().pupilData

    override suspend fun createServicesManager(): ServicesManager {
        val app = this@KtorClassChartsClient
        return RuntimeServicesManager {
            throwOnError()
        } .apply {
            registerService(KtorWebService(app, data), WebService::class)
            registerService(WebHomeworkService(app), HomeworkService::class)
            registerService(WebLessonsService(app), LessonsService::class)
            registerService(WebDetentionsService(app), DetentionsService::class)
        }
    }
}

class KtorClassChartsClientBuilder {
    var connectData: ConnectData? = null

    fun build(): KtorClassChartsClient {
        val data = requireNotNull(connectData) { "Connect data is not set!" }
        return KtorClassChartsClient(Environment.Empty, data)
    }
}

public suspend inline fun KtorClassChartsClient(
    block: KtorClassChartsClientBuilder.() -> Unit
): KtorClassChartsClient {
    val client = KtorClassChartsClientBuilder().apply(block).build()
    client.init()
    return client
}