@file:OptIn(ExperimentalContracts::class)

package dev.nikdekur.classcharts

import kotlinx.datetime.LocalDate
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

interface ConnectData {

    val studentCode: StudentCode?
    val dateOfBirth: LocalDate?

    val sessionId: String?
    fun setSessionId(sessionId: String)
}

class ConnectDataBuilder {
    var studentCode: StudentCode? = null
    var dateOfBirth: LocalDate? = null
    var sessionId: String? = null
    var onSessionIdChangeFunc: ((String) -> Unit)? = null

    fun onSessionIdChange(func: (String) -> Unit) {
        onSessionIdChangeFunc = func
    }

    fun build(): ConnectData {
        println("Building with studentCode: $studentCode, dateOfBirth: $dateOfBirth, sessionId: $sessionId")
        val sessionId = sessionId
        if (sessionId == null) {
            requireNotNull(studentCode) { "Student code is not set!" }
            requireNotNull(dateOfBirth) { "Date of birth is not set!" }
        }

        return object : ConnectData {
            override val studentCode = this@ConnectDataBuilder.studentCode
            override val dateOfBirth = this@ConnectDataBuilder.dateOfBirth

            private var _sessionId: String? = sessionId

            override val sessionId
                get() = _sessionId

            override fun setSessionId(sessionId: String) {
                _sessionId = sessionId
                onSessionIdChangeFunc?.invoke(sessionId)
            }
        }
    }
}

public inline fun ConnectData(
    block: ConnectDataBuilder.() -> Unit
): ConnectData {
    contract {
        callsInPlace(block, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
    }

    return ConnectDataBuilder().apply(block).build()
}