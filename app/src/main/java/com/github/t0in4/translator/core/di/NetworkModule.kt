package com.github.t0in4.translator.core.di

import android.content.ContentProviderOperation.newCall
import com.github.t0in4.translator.BuildConfig
import com.github.t0in4.translator.core.TranslationApi
import dagger.Module
import dagger.Provides
import dagger.Lazy
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttp(): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                }
            )
            .build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json, okHttp: Lazy<Call.Factory>): Retrofit {
        return Retrofit.Builder()
            //.baseUrl("https://api.github.com")
            .baseUrl("https://ftapi.pythonanywhere.com/")
            .callFactory {
                okHttp.get().newCall(it)
            }
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }
      @Provides
      @Singleton
      fun provideTranslationApi(retrofit: Retrofit): TranslationApi {
          return retrofit.create(TranslationApi::class.java)
      }
}
