package com.sergey.weatherforecast.di.coroutines

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CoroutineScopesModule {

    @Singleton
    @Provides
    fun provideCoroutineExceptionHandler(
    ) : CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
    }

    @Singleton
    @Provides
    fun provideCoroutineScope(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        exceptionHandler: CoroutineExceptionHandler
    ) : CoroutineScope {
        return CoroutineScope(SupervisorJob() + dispatcher + exceptionHandler)
    }

}
