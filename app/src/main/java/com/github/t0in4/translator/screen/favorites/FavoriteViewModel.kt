package com.github.t0in4.translator.screen.favorites

import androidx.lifecycle.ViewModel
import com.github.t0in4.translator.core.favoritedata.FavoriteDao
import com.github.t0in4.translator.core.favoritedata.FavoriteTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : ViewModel() {
    fun getFavorites(): Flow<List<FavoriteTable>> {
        return favoriteDao.getFavorites()
    }
}