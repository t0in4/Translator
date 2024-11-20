package com.github.t0in4.translator.core.favoritedata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavorite(favorite: FavoriteTable)

    @Query("SELECT * FROM favorites ORDER BY timestamp")
    fun getFavorites(): Flow<List<FavoriteTable>>
}