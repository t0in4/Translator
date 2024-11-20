package com.github.t0in4.translator.core.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.t0in4.translator.core.favoritedata.FavoriteDao
import com.github.t0in4.translator.core.favoritedata.FavoriteTable

@Database(entities = [TranslationHistory::class, FavoriteTable::class], version = 2)
abstract class AppDatabase : RoomDatabase() {


    abstract fun translationHistoryDao(): TranslationHistoryDao
    abstract fun favoriteDao(): FavoriteDao
    companion object {
        @Volatile // потокобезопасный обрашение к этой переменной
        // будет безопасной для различных потоков
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // synchronized - означает, что переменная будет не доступной
            // другим потокам, если какой-то поток уже здесь что то делает
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database",
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }

        }
    }
}