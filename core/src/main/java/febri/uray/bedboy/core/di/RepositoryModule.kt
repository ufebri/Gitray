package febri.uray.bedboy.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import febri.uray.bedboy.core.data.source.AppRepository
import febri.uray.bedboy.core.domain.repository.IAppRepository

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(appRepository: AppRepository): IAppRepository

}