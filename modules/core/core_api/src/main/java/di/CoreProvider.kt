package di

import android.content.Context
import data.DataStoreManager
import data.FileManager
import data.NetworkRepository
import data.ResourceManager
import kotlinx.coroutines.CoroutineDispatcher


interface CoreProvider {
    fun getNetworkRepository(): NetworkRepository

    fun getDataStoreManager(): DataStoreManager

    fun getAppContext(): Context

    fun gerResourceManager(): ResourceManager

    fun getCoroutineDispatcher(): CoroutineDispatcher

    fun getFileManager(): FileManager

}