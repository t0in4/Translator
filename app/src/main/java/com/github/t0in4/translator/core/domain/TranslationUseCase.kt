package com.github.t0in4.translator.core.domain

import com.github.t0in4.translator.core.TranslationApi
import javax.inject.Inject

//import jakarta.inject.Inject

class TranslationUseCase  @Inject constructor (
    private val translationApi: TranslationApi
){
    suspend fun translate() {

    }
}
