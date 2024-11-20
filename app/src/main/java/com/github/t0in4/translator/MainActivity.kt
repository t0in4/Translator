package com.github.t0in4.translator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.t0in4.translator.navigation.NavRoutes
import com.github.t0in4.translator.screen.favorites.FavoriteScreen
import com.github.t0in4.translator.screen.history.HistoryScreen
import com.github.t0in4.translator.screen.translation.TranslationScreen
import com.github.t0in4.translator.ui.theme.TranslatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslatorTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar()
                    }) { innerPadding ->

                }*/
                MainScreen()
            }
        }
    }
}


    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "translate"
            ) {
                composable("chat") {}
                composable("camera") {}
                composable(NavRoutes.TranslationScreen.route) { TranslationScreen(navController) }
                composable(NavRoutes.HistoryScreen.route) { HistoryScreen(navController) }
                composable(NavRoutes.FavoriteScreen.route) { FavoriteScreen(navController) }
            }
        }
    }

    private val Destinations = listOf("chat", "camera", "translate", "history", "favorite")

    /*@Composable
fun BottomNavigationBar() {
    NavigationBar(
        *//*backgroundColor = MaterialTheme.colors.primary,
       contentColor = MaterialTheme.colors.primary,*//*

    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.fluent_mic_24_filled),
                    contentDescription = "Chat"
                )
            },
            selected = false,
            onClick = {

            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_baseline_photo_camera),
                    contentDescription = "Chat"
                )
            },
            selected = false,
            onClick = {
            }
        )
        CustomNavigationBarItem(
            ImageVector.vectorResource(id = R.drawable.mdi_translate__1_),
            //ImageBitmap.imageResource(R.drawable.mdi_translate),
            " ",
            false,
        )
        NavigationBarItem(
            icon = {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.material_symbols_history),
                    contentDescription = ""
                )
            },
            selected = false,
            onClick = {
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_round_star_border),

                    contentDescription = ""
                )
            },
            selected = false,
            onClick = {

            }
        )
    }
}

@Composable
fun CustomNavigationBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    //onClick: () -> Unit
) {
    val painter = rememberVectorPainter(image = icon)
    Canvas(
        modifier = Modifier
            .width(69.dp)
            .height(69.dp)
            .clipToBounds()
            .clickable(onClick = {})
    ){
        drawCircle(color = Color.Blue, radius = 69f)
        translate(left = 60f, top = 60f) {
            with(painter) {
                draw(
                    size = intrinsicSize,
                )
            }
        }

    }
}*/

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        var selectedItem = rememberSaveable { mutableIntStateOf(2) }
        var intValue = selectedItem.value
        val icons = listOf(
            ImageVector.vectorResource(id = R.drawable.fluent_mic_24_filled),
            ImageVector.vectorResource(id = R.drawable.ic_baseline_photo_camera),
            ImageVector.vectorResource(id = R.drawable.mdi_translate__1_),
            ImageVector.vectorResource(id = R.drawable.material_symbols_history),
            ImageVector.vectorResource(id = R.drawable.ic_round_star_border),
        )
        NavigationBar(
            content = {
                Destinations.forEachIndexed { index, item ->
                    if (item.equals("translate")) {
                        CustomNavigationBarItem(
                            icons[index],
                            label = item,
                            selected = intValue == index,
                            /*onClick = {
                                intValue = index
                                navController.navigate(item)
                            }*/
                            intValue = index,
                            navController = navController
                        )
                    } else {
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = icons[index],
                                    contentDescription = item
                                )
                            },
                            label = { Text(item) },
                            selected = intValue == index,
                            onClick = {
                                intValue = index
                                navController.navigate(item)
                            }
                        )
                    }
                }

            }
        )
    }

    @Composable
    fun RowScope.BottomNavigationItem(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        alwaysShowLabel: Boolean = true,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,

        ) {
        NavigationBarItem(
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            alwaysShowLabel = alwaysShowLabel,
            icon = if (selected) selectedIcon else icon,
            label = label,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = NavigationDefaults.navigationContentColor(),
                selectedTextColor = NavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = NavigationDefaults.navigationContentColor(),
                indicatorColor = NavigationDefaults.navigationIndicatorColor(),
            )
        )
    }
    object NavigationDefaults {
        @Composable
        fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

        @Composable
        fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

        @Composable
        fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
    }
@Composable
fun CustomNavigationBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    //onClick: () -> Unit
    intValue: Int,
    navController: NavController
) {
    val painter = rememberVectorPainter(image = icon)
    Canvas(
        modifier = Modifier
            .width(69.dp)
            .height(69.dp)
            .clipToBounds()
            .clickable(onClick = { navController.navigate(label) })
    ){
        drawCircle(color = Color.Blue, radius = 69f)
        translate(left = 60f, top = 60f) {
            with(painter) {
                draw(
                    size = intrinsicSize,
                )
            }
        }
    }
}
