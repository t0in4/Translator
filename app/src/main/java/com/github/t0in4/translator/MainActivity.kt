package com.github.t0in4.translator

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.t0in4.translator.ui.theme.TranslatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar()
                    }) { innerPadding ->

                }
            }
        }
    }
}



@Composable
fun BottomNavigationBar() {
    NavigationBar(
        /*backgroundColor = MaterialTheme.colors.primary,
       contentColor = MaterialTheme.colors.primary,*/

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
          /*  .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {

                                    }
                                )
            }*/,
        "",
        /*onDraw = {
            drawCircle(color = Color.Blue, radius = 39f)
            with(painter) {
                draw(
                    size = intrinsicSize,
                )
            }

        },*/

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




