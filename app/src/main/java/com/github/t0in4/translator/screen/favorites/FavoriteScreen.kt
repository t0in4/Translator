package com.github.t0in4.translator.screen.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.t0in4.translator.core.favoritedata.FavoriteTable
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteList = viewModel.getFavorites().collectAsState(initial = emptyList())
    Column(
       modifier = Modifier.fillMaxSize()
    ){
        TopAppBar(title = { Text("Favorites")} )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ){
            items(favoriteList.value) {
                favorite ->
                FavoriteItem(favorite)
            }
        }
    }
}

@Composable
fun FavoriteItem(favorite: FavoriteTable) {
    Column(
       modifier = Modifier.padding(vertical = 8.dp)
    ){
        Text(text = "Source: ${favorite.sourceText}")
        Text(text = "Translation: ${favorite.translatedText}")
        Text(text = "Time: ${SimpleDateFormat().format(favorite.timestamp)}")

    }
}