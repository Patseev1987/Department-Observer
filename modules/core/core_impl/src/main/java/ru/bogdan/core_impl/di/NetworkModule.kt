package ru.bogdan.core_impl.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import data.DataStoreManager
import data.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.bogdan.core_impl.data.network.NetWorkClientApplication
import ru.bogdan.core_impl.data.network.NetworkRepositoryImpl
import ru.bogdan.core_impl.data.network.mapers.MapperWeb

@Module
interface NetworkModule {
    @AppScope
    @Binds
    fun bindsNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository

    companion object {
        @Provides
        @AppScope
        fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @AppScope
        fun provideNetWorkClientApplication(dataStoreManager: DataStoreManager): NetWorkClientApplication =
            NetWorkClientApplication(dataStoreManager)

        @Provides
        @AppScope
        fun provideMapperWeb() = MapperWeb()

    }
}