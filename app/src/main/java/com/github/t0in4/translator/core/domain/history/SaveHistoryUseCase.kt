package com.github.t0in4.translator.core.domain.history

import com.github.t0in4.translator.core.data.TranslationHistory
import com.github.t0in4.translator.core.data.TranslationHistoryDao
import org.checkerframework.checker.units.qual.h
import javax.inject.Inject

class SaveHistoryUseCase @Inject constructor(
    private val translationHistoryDao: TranslationHistoryDao,
) {
    suspend fun save(sourceText: String, translatedText: String) {
        translationHistoryDao.insertHistory(TranslationHistory(
            sourceText = sourceText,
            translatedText = translatedText,
        ))
    }
}