package com.github.t0in4.translator.core.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
                )//.fallbackToDestructiveMigration() // подходит для кэширования данных из интернета
                    //.addMigrations() // лучше создать отдельный список и впоследствии дополнять миграции
                    .addMigrations(MIGRATION_1_2) // если следующая таблица то через запятую указывать ее
                    .build()
                INSTANCE = instance
                instance
            }

        }
    }
}

val MIGRATION_1_2 = object: Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE IF NOT EXISTS favorites (
                    id INTEGER PIMARY KEY AUTOINCREMENT NOT NULL,
                    sourceText TEXT NOT NULL,
                    translatedText TEXT NOT NULL,
                    timestamp INTEGER NOT NULL
                )
            """.trimIndent()
        )
    }
}