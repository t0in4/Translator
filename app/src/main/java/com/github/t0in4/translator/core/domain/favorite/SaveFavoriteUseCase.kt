package com.github.t0in4.translator.core.domain.favorite

import com.github.t0in4.translator.core.favoritedata.FavoriteDao
import com.github.t0in4.translator.core.favoritedata.FavoriteTable
import org.checkerframework.checker.units.qual.t
import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(
    private val favoriteDao: FavoriteDao,

) {
    suspend fun save(sourceText: String, translatedText: String) {
        favoriteDao.insertFavorite(FavoriteTable(
            sourceText = sourceText,
            translatedText = translatedText
        ))
    }
}