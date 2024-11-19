package com.github.t0in4.translator.core.domain.translation

import com.github.t0in4.translator.core.TranslationApi
import com.github.t0in4.translator.core.domain.LanguageCode
import javax.inject.Inject

//import jakarta.inject.Inject

class TranslationUseCase  @Inject constructor (
    private val translationApi: TranslationApi
){
    suspend fun translate(sl: LanguageCode, dl: LanguageCode, text: String): com.github.t0in4.translator.core.TranslationResponse {

        return translationApi.translate(sl.code, dl.code, text)
    }
}
