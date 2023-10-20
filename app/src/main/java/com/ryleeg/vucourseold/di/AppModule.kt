package com.ryleeg.vucourseold.di

import android.content.Context
import com.ryleeg.vucourseold.PreferencesManager
import com.ryleeg.vucourseold.data.AuthInterceptor
import com.ryleeg.vucourseold.data.VuApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideHttpInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun providesPreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        preferencesManager: PreferencesManager
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(AuthInterceptor(preferencesManager))
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()

    @Provides
    @Singleton
    fun provideMoshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi = Moshi.Builder()
        .add(kotlinJsonAdapterFactory)
        .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        okHttp: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("http://192.168.1.2:8080/api/")
            .client(okHttp)
            .addConverterFactory(moshiConverterFactory).build()
    }

    @Provides
    fun providesVuApi(retrofit: Retrofit): VuApi =
        retrofit.create(VuApi::class.java)

}