package com.github.t0in4.translator.screen.translation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.t0in4.translator.screen.favorites.FavoriteViewModel
import com.github.t0in4.translator.ui.LanguageSelector
import com.github.t0in4.translator.ui.TextInput
import com.github.t0in4.translator.ui.TranslateButton
import com.github.t0in4.translator.ui.TranslationResult


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TranslationScreen(
    navController: NavController,
    viewModel: TranslationViewModel = hiltViewModel(),
    viewModelF: FavoriteViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(title = { Text("Translation App") })
        LanguageSelector(
            sourceLanguage = uiState.value.sourceLang,
            targetLanguage = uiState.value.targetLang,
            onSwapLanguage = { viewModel.swapLanguage() },
            onSelectSourceLanguage = { viewModel.selectSourceLanguage(it) },
            onSelectTargetLanguage = { viewModel.selectTargetLanguage(it) }
        )

        TextInput(
            language = uiState.value.sourceLang.title,
            text = uiState.value.inputText,
            onTextChange = { viewModel.updateInputText(it) },
            onClearText = { viewModel.clearInputText() },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TranslateButton(
            onTranslate = { viewModel.translateText() },
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        uiState.value.translateText?.let {
            TranslationResult(
                result = it,
                modifier = Modifier.padding(horizontal = 16.dp),
                onSave  = { viewModel.insertFavorite() }
            )
        }
    }
}



