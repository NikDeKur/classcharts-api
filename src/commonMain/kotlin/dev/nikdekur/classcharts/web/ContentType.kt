package dev.nikdekur.classcharts.web

data class ContentType(
    val type: String,
    val subtype: String,
    val parameters: Map<String, String> = emptyMap()
) {

    object Application  {
        val Json = ContentType("application", "json")
        val FormUrlEncoded = ContentType("application", "x-www-form-urlencoded")
    }
}
