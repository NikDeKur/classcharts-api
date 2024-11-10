package dev.nikdekur.classcharts

import dev.nikdekur.ndkore.service.manager.OnServiceOperation
import dev.nikdekur.ornament.service.AbstractAppService

abstract class ClassChartsService : AbstractAppService<ClassChartsClient>() {
    override fun onError(operation: OnServiceOperation, e: Exception) {
        throw e
    }
}