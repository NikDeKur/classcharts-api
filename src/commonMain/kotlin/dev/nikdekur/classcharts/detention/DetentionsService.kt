package dev.nikdekur.classcharts.detention

fun interface DetentionsService {

    suspend fun getDetentions(): Collection<Detention>
}