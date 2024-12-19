package com.example.ptathome.externalresources.restresources

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object RestModule {

    @youtube
    @Provides
    fun provideYoutubeRestService(): RestInterface {
        return YoutubeRestService()
    }

    @wikipedia
    @Provides
    fun provideWikipedia(): RestInterface {
        return WikipediaRestService()
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class youtube

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class wikipedia


