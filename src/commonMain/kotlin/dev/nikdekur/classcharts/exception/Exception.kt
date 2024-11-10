package dev.nikdekur.classcharts.exception

data class LoginFailedException(
    val problem: Problem,
    override val message: String
) : RuntimeException(message) {

    enum class Problem {
        INVALID_STUDENT_CODE,
        INVALID_DATE_OF_BIRTH,
        UNKNOWN
    }
}

data class PingFailedException(
    override val message: String,
    val resetSession: Boolean = false
) : RuntimeException(message)