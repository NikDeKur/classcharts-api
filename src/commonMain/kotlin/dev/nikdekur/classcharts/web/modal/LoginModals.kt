package dev.nikdekur.classcharts.web.modal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    val code: String,
    val dob: String,

    @SerialName("remember_me")
    val rememberMe: Boolean,

    @SerialName("recaptcha-token")
    val recaptchaToken: String
)



@Serializable
data class LoginResponse(
    val data: PupilDataModal,
    val meta: Meta
) : ClassChartsResponse() {

    @Serializable
    data class Meta(
        val version: String? = null,

        @SerialName("session_id")
        val sessionId: String? = null
    )
}



@Serializable
data class PingRequest(
    /**
     * Whether to reset the session id (will include a new session id in the response)
     *
     * If set to false, previous session id will be renewed and able to be used
     */
    @SerialName("include_data")
    val includeData: Boolean
)


@Serializable
data class PingResponse(
    val data: PingData,
    val meta: Meta
) : ClassChartsResponse() {

    @Serializable
    data class PingData(
        val user: PupilDataModal
    )

    @Serializable
    data class Meta(
        val version: String? = null,

        @SerialName("session_id")
        val sessionId: String? = null
    )
}

