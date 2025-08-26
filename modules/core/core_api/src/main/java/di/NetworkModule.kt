package di

import dagger.Binds
import dagger.Module
import dagger.Provides
import data.dataStore.DataStoreManager
import data.network.NetWorkClientApplication
import data.network.NetworkRepository
import data.network.NetworkRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

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
    }
}