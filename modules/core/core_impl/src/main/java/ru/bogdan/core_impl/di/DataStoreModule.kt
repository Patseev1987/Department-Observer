package ru.bogdan.core_impl.di

import dagger.Binds
import dagger.Module
import data.DataStoreManager
import data.FileManager
import ru.bogdan.core_impl.data.dataStore.DataStoreManagerImpl
import ru.bogdan.core_impl.data.fileManager.FileManagerImpl


@Module
interface DataStoreModule {
    @AppScope
    @Binds
    fun bindDataStore(dataStoreManager: DataStoreManagerImpl): DataStoreManager

    @AppScope
    @Binds
    fun bindFileManager(fileManager: FileManagerImpl): FileManager
}