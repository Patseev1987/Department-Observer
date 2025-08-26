package di

import dagger.Binds
import dagger.Module
import data.resorseMenager.ResourceManager
import data.resorseMenager.ResourceManagerImpl

@Module
interface ResourceManagerModule {
    @AppScope
    @Binds
    fun bindResourceManager(impl: ResourceManagerImpl): ResourceManager
}