package di

import dagger.Binds
import dagger.Module
import data.dataStore.DataStoreManager
import data.dataStore.DataStoreManagerImpl


@Module
interface DataStoreModule {
    @AppScope
    @Binds
    fun bindDataStore(dataStoreManager: DataStoreManagerImpl): DataStoreManager
}