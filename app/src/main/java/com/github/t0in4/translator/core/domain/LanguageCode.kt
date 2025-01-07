package com.github.t0in4.translator.core.domain

import androidx.annotation.DrawableRes
import com.github.t0in4.translator.R

enum class LanguageCode(val code: String, @DrawableRes val flagIconRes: Int, val title: String) {
    // @DrawableRes - напоминает о том, что Int в параметре, это из Drawable
    ENGLISH("en", R.drawable.ic_flag_english, "English"),
    RUSSIAN("ru", R.drawable.ic_flag_russian, "Russian"),
    GERMAN("de", R.drawable.ic_flag_deutch, "Deutch"),
    SPANISH("es", R.drawable.ic_flag_spain, "Spain")
}