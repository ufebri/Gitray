package com.uray.gitray.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import febri.uray.bedboy.core.domain.usecase.AppInteractor
import febri.uray.bedboy.core.domain.usecase.AppUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideAppUseCase(appInteractor: AppInteractor): AppUseCase

}