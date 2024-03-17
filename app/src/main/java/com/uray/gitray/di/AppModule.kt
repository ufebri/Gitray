package com.uray.gitray.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import febri.uray.bedboy.core.domain.usecase.AppInteractor
import febri.uray.bedboy.core.domain.usecase.AppUseCase

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideAppUseCase(appInteractor: AppInteractor): AppUseCase

}