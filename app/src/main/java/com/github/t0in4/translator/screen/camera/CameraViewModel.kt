package com.github.t0in4.translator.screen.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.github.t0in4.translator.core.domain.camera.GetTextFromImageUseCase
import com.github.t0in4.translator.core.domain.translation.TranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor (
    private val getTextFromImageUseCase: GetTextFromImageUseCase,
    private val translationUseCase: TranslationUseCase
) : ViewModel() {
    fun setTextFromImage(bitmap: Bitmap): String {
        return getTextFromImageUseCase.recognizeTextFromImage(bitmap)
    }
}