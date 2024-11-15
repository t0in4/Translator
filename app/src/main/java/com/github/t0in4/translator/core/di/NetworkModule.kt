package com.github.t0in4.translator.core.di

import com.github.t0in4.translator.core.TranslationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    //@Singleton
    fun provideRetrofit(okhttpCallFactory: dagger.Lazy<Call.Factory>): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .build()
    }
    @Provides
    //@Singleton
    fun provideTranslationApi(retrofit: Retrofit): TranslationApi {
        return retrofit.create(TranslationApi::class.java)
    }
}
