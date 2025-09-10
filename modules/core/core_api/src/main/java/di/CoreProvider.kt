package di

import android.content.Context
import data.dataStore.DataStoreManager
import data.database.DataBaseRepository
import data.fileManager.FileManager
import data.network.NetworkRepository
import data.resorseMenager.ResourceManager
import kotlinx.coroutines.CoroutineDispatcher


interface CoreProvider {
    fun getNetworkRepository(): NetworkRepository

    fun getADatabaseRepository(): DataBaseRepository

    fun getDataStoreManager(): DataStoreManager

    fun getAppContext(): Context

    fun gerResourceManager(): ResourceManager

    fun getCoroutineDispatcher(): CoroutineDispatcher

    fun getFileManager(): FileManager

}