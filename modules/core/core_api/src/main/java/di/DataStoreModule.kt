package di

import dagger.Binds
import dagger.Module
import data.dataStore.DataStoreManager
import data.dataStore.DataStoreManagerImpl
import data.fileManager.FileManager
import data.fileManager.FileManagerImpl


@Module
interface DataStoreModule {
    @AppScope
    @Binds
    fun bindDataStore(dataStoreManager: DataStoreManagerImpl): DataStoreManager

    @AppScope
    @Binds
    fun bindFileManager(fileManager: FileManagerImpl): FileManager
}