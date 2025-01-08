package com.example.ptathome.externalresources.network

import android.app.Application
import com.example.ptathome.externalresources.gson.GsonManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Inject and provide Network manager and Gson manager with dagger hilt
 *
 * @author Oscar Sandberg
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideNetworkManager(application: Application, gsonManager: GsonManager): NetworkManager {
        return NetworkManager(application,gsonManager)
    }

    @Provides
    fun provideGsonManager(): GsonManager {
        return GsonManager()
    }
}