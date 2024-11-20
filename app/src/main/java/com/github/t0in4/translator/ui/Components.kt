package com.github.t0in4.translator.ui

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.github.t0in4.translator.R

@Composable
fun TextInput(language: String, text: String, onTextChange: (String) -> Unit, onClearText: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = language)
        OutlinedTextField(value = text, onValueChange = onTextChange, modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter text here...") }, trailingIcon = {
                IconButton(onClick = onClearText) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear text")
                }
            })
    }
}

@Composable
fun TranslateButton(onTranslate: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = onTranslate,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(9.dp)
        ){
            Text("Translate")
        }
    }
}


@Composable
fun TranslationResult(result: String, modifier: Modifier = Modifier, onSave: () -> Unit) {
    Card(modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        OutlinedTextField(
            value = result,
            onValueChange = {},
            readOnly = true,
            modifier = modifier.fillMaxWidth()
        )
            Icon(
                ImageVector.vectorResource(id = R.drawable.favoritestar),
                "save to favorite",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 15.dp)
                    .clickable(
                        onClick = onSave
                    ),
            )
    }
}

@Composable
fun LanguageSelector(sourceLanguage: String, targetLanguage: String, onSwapLanguages: () -> Unit) {
    // TODO: Добавьте сюда реализацию селектора языков
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("english", "russian")

    /*DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEach {
            option ->
            DropdownMenuItem(
                text = { Text(text = option) },
                onClick = {

                }
            )
        }
    }*/

}
