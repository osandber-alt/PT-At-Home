package com.example.ptathome.externalresources.restresources

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/**
 * Inject and provide Youtube rest service and Wikipedia rest service with dagger hilt
 *
 * @author Oscar Sandberg
 */
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

/**
 *
 * Qualifier used to describe that an input is of the annotation class youtube
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class youtube

/**
 *
 * Qualifier used to describe that an input is of the annotation class wikipedia
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class wikipedia


