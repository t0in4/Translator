package com.github.t0in4.translator.screen.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.github.t0in4.translator.screen.translation.TranslationViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import java.io.File

@Composable
fun CameraScreen(
    translationViewModel: TranslationViewModel = hiltViewModel()

) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProvider = remember { ProcessCameraProvider.getInstance(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val recognizeTextState = remember { mutableStateOf<String>("") }

    //val viewModel: CameraViewModel by viewModel()
    val uiState = translationViewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            cameraProviderFuture = cameraProvider,
            lifecycleOwner = lifecycleOwner,
            imageCapture = imageCapture,
            onClick = {
                val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
                    createTempFile()
                ).build()
                imageCapture.takePicture(
                    outputFileOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            val uri = outputFileResults.savedUri
                            uri?.let{
                                val bitmap = BitmapFactory.decodeFile(it.path)
                                imageBitmap.value = bitmap
                                recognizeTextFromImage(bitmap, recognizeTextState, translationViewModel)
                            }
                        }
                        override fun onError(exception: ImageCaptureException) {

                        }
                    }
                )
            }
        )
        Text(
            text = recognizeTextState.value.toString(),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
        
       /* imageBitmap.value?.let { bitmap ->
            Image(bitmap = bitmap.asImageBitmap(),
                contentDescription = "Captured photo",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }*/
    }
}

@Composable
fun CameraPreview(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifecycleOwner: LifecycleOwner,
    imageCapture: ImageCapture,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    // camera Preview
    val previewView = remember { PreviewView(context) }
    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = androidx.camera.core.Preview.Builder().build()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        cameraProvider.unbindAll() // отключаем всех клиентов
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.surfaceProvider = previewView.surfaceProvider
    }
    AndroidView(
        factory = { previewView },
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
    )
}

private fun createTempFile(): File {
    return File.createTempFile("cameraImg", System.nanoTime().toString())
}

private fun recognizeTextFromImage(bitmap: Bitmap,
                                   recognizeTextState: MutableState<String>,
                                   viewModel: TranslationViewModel
) {
    val inputImage = InputImage.fromBitmap(bitmap, 0)
    val textRecognizer: TextRecognizer = TextRecognition.getClient()
    textRecognizer.process(inputImage).addOnSuccessListener { result ->
        val recognizedText = result.textBlocks.flatMap { blocks ->
            blocks.lines.map { it.text }


        }
        viewModel.updateInputText(recognizedText.toString())
        viewModel.translateText()

        recognizeTextState.value = viewModel.uiState.value.translateText.orEmpty()

    }
        .addOnFailureListener {
            recognizeTextState.value = "Text Recognition Failed"// getmentor сайте
        }
}


