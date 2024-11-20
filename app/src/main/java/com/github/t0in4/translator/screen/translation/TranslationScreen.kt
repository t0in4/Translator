package com.github.t0in4.translator.screen.translation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.t0in4.translator.R
import com.github.t0in4.translator.screen.favorites.FavoriteViewModel
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
    var itemPositionLeft = remember {
        mutableStateOf(0)
    }
    var itemPositionRight = remember {
        mutableStateOf(3)
    }
    val Languages = listOf("English", "Spanish", "Russian", "German")
    var expandedLeft by remember { mutableStateOf(false) }
    var expandedRight by remember { mutableStateOf(false) }
    var leftState by remember { mutableStateOf("") }
    var rightState by remember { mutableStateOf("") }
    var checker by remember { mutableStateOf(false) }
    val onCheckerChange = { checker = !checker}
    val onClick = { viewModel.swapLanguage() }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(title = { Text("Translation App") })
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(14.dp)),
        ) {
            Box() {
                Row(
                    modifier = Modifier.clickable {
                        expandedLeft = true
                    }
                ) {
                     leftState = Languages[itemPositionLeft.value]
                    Text(if (checker) rightState else leftState)
                    uiState.value.sourceLang = leftState
                }
                DropdownMenu(
                    expanded = expandedLeft,
                    onDismissRequest = { expandedLeft = false }
                ) {
                    Languages.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                expandedLeft = false
                                itemPositionLeft.value = index
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.padding(16.dp))
            Box() {
                val painter =
                    rememberVectorPainter(ImageVector.vectorResource(R.drawable.resource_switch))
                Canvas(
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .clipToBounds()
                        .clickable(onClick =  {
                            onCheckerChange()
                            onClick()
                        })
                ) {
                    with(painter) {
                        draw(
                            size = intrinsicSize
                        )
                    }
                }
            }

            Spacer(Modifier.padding(16.dp))

            Box() {
                Row(
                    modifier = Modifier.clickable {
                        expandedRight = true
                    }
                ) {
                     rightState = Languages[itemPositionRight.value]
                    Text(text = if (checker) leftState else rightState)
                    uiState.value.targetLang = rightState
                }
                DropdownMenu(
                    expanded = expandedRight,
                    onDismissRequest = { expandedRight = false }
                ) {
                    Languages.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                expandedRight = false
                                itemPositionRight.value = index
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        TextInput(
            language = uiState.value.sourceLang,
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



