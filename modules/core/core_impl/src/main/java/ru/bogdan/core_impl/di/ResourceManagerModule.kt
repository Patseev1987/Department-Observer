package ru.bogdan.core_impl.di

import dagger.Binds
import dagger.Module
import data.ResourceManager
import ru.bogdan.core_impl.data.resorseMenager.ResourceManagerImpl

@Module
interface ResourceManagerModule {
    @AppScope
    @Binds
    fun bindResourceManager(impl: ResourceManagerImpl): ResourceManager
}