package com.github.t0in4.translator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.github.t0in4.translator.R
import com.github.t0in4.translator.core.domain.LanguageCode
import org.checkerframework.checker.units.qual.t

@Composable
fun TextInput(
    language: String,
    text: String,
    onTextChange: (String) -> Unit,
    onClearText: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = language)
        OutlinedTextField(value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter text here...") },
            trailingIcon = {
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
        ) {
            Text("Translate")
        }
    }
}


@Composable
fun TranslationResult(result: String, modifier: Modifier = Modifier, onSave: () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
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
fun LanguageSelector(
    sourceLanguage: LanguageCode,
    targetLanguage: LanguageCode,
    onSwapLanguage: () -> Unit,
    onSelectSourceLanguage: (LanguageCode) -> Unit,
    onSelectTargetLanguage: (LanguageCode) -> Unit
) {
    var showLanguageDialog by remember { mutableStateOf(false) }
    var isLeftIconSelected by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FlagIcon(
            iconRes = sourceLanguage.flagIconRes,
            onClick = {
                isLeftIconSelected = true
                showLanguageDialog = true
            }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_swap),
            contentDescription = "Swap",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onSwapLanguage)
        )
        FlagIcon(
            iconRes = targetLanguage.flagIconRes,
            onClick = {
                isLeftIconSelected = false
                showLanguageDialog = true
            }
        )

    }
    if (showLanguageDialog) {
        LanguageSelectionDialog(
            languages = LanguageCode.entries,
            onLanguageSelected = {
                if (isLeftIconSelected) {
                    onSelectSourceLanguage(it)
                } else {
                    onSelectTargetLanguage(it)
                }
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}

@Composable
fun FlagIcon(iconRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "Flag",
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun LanguageSelectionDialog(
    languages: List<LanguageCode>,
    onLanguageSelected: (LanguageCode) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = { Text("Select Lanugage") },
        text = {
            Column {
                languages.forEach { language ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLanguageSelected(language) }
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = language.flagIconRes),
                            contentDescription = language.title,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = language.title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(16.dp)
    )

}
