package com.github.t0in4.translator.screen.history

import androidx.lifecycle.ViewModel
import com.github.t0in4.translator.core.data.TranslationHistory
import com.github.t0in4.translator.core.data.TranslationHistoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
private val translationHistoryDao: TranslationHistoryDao,
): ViewModel() {
    fun getHistory(): Flow<List<TranslationHistory>> {
        return translationHistoryDao.getTranslationHistory()
    }
}