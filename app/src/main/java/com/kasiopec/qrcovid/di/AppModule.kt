package com.kasiopec.qrcovid.di

import android.content.Context
import android.content.SharedPreferences
import com.kasiopec.qrcovid.PrefsManager
import com.kasiopec.qrcovid.QRView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        private const val PREF_USER_PREFERENCES = "USER_PREFS"
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_USER_PREFERENCES, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providePrefsManager(preferences: SharedPreferences) = PrefsManager(preferences)

    @Provides
    fun provideQrView(@ApplicationContext context: Context): QRView = QRView(context)
}