package com.github.t0in4.translator.screen.translation

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.t0in4.translator.core.domain.LanguageCode
import com.github.t0in4.translator.core.domain.favorite.SaveFavoriteUseCase
import com.github.t0in4.translator.core.domain.history.SaveHistoryUseCase
import com.github.t0in4.translator.core.domain.translation.TranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    //api
    private val translationUseCase: TranslationUseCase,
    private val saveHistoryUseCase: SaveHistoryUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TranslationUiState("", "", ""))
    val uiState: StateFlow<TranslationUiState> = _uiState

    fun updateInputText(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun clearInputText() {
        _uiState.update { it.copy(inputText = "") }
    }

    fun swapLanguage() {
        _uiState.update {
            it.copy(
                sourceLang = it.targetLang,
                targetLang = it.sourceLang

            )

        }
    }

    fun translateText() {
        viewModelScope.launch {
            val result = translationUseCase.translate(
                sl =  LanguageCode.valueOf(_uiState.value.sourceLang.uppercase()),
                dl = LanguageCode.valueOf(_uiState.value.targetLang.uppercase()),
                //dl = LanguageCode.valueOf(code.uppercase()),
                text = _uiState.value.inputText,
            )
            _uiState.update {
                it.copy(
                    translateText = result.translations.possibleTranslations.firstOrNull()
                        ?: _uiState.value.inputText
                )
            }
            saveHistoryUseCase.save(_uiState.value.inputText, _uiState.value.translateText.orEmpty())
                                                                              // orEmpty - запишет вместо null пустую строку
        }
    }
    fun insertFavorite() {
        viewModelScope.launch {
            val result = translationUseCase.translate(
                sl =  LanguageCode.valueOf(_uiState.value.sourceLang.uppercase()),
                dl = LanguageCode.valueOf(_uiState.value.targetLang.uppercase()),
                //dl = LanguageCode.valueOf(code.uppercase()),
                text = _uiState.value.inputText,
            )
            _uiState.update {
                it.copy(
                    translateText = result.translations.possibleTranslations.firstOrNull()
                        ?: _uiState.value.inputText
                )
            }
            saveFavoriteUseCase.save(_uiState.value.inputText, _uiState.value.translateText.orEmpty())

        }
    }

}

data class TranslationUiState(
    var sourceLang: String ,
    var targetLang: String ,
    var inputText: String ,
    var translateText: String? = null,
)
