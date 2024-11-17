package com.github.t0in4.translator.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {
    @GET("translate")
    suspend fun translate(
        @Query("sl") sourceLanguage: String,
        @Query("dl") destinationLanguage: String,
        @Query("text") text: String
    ): TranslationResponse
}
@kotlinx.serialization.Serializable
data class TranslationResponse(
    val pronunciation: Pronunciation,
    val translations: Translations,
)

@kotlinx.serialization.Serializable
data class Pronunciation(
    @SerialName("source-text-audio") val sourceTextAudio: String?,
    @SerialName("destination-text-audio") val destinationTextAudio: String?,
)

@Serializable
data class Translations(
    @SerialName("possible-translations") val possibleTranslations: List<String>,
)
