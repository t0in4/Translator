package com.github.t0in4.translator.navigation

sealed class NavRoutes(val route: String) {
    object HistoryScreen : NavRoutes("history")
    object TranslationScreen : NavRoutes("translate")
    object FavoriteScreen  : NavRoutes("favorite")
}