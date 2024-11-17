package com.github.t0in4.translator.core.domain

import android.view.translation.TranslationResponse
import com.github.t0in4.translator.core.TranslationApi
import javax.inject.Inject

//import jakarta.inject.Inject

class TranslationUseCase  @Inject constructor (
    private val translationApi: TranslationApi
){
    suspend fun translate(sl: LanguageCode, dl: LanguageCode, text: String): com.github.t0in4.translator.core.TranslationResponse {

        return translationApi.translate(sl.code, dl.code, text)
    }
}
