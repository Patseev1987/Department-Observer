package ru.bogdan.core_impl.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import data.DataBaseRepository
import ru.bogdan.core_impl.data.database.AppDatabase
import ru.bogdan.core_impl.data.database.DataBaseRepositoryImpl

@Module
interface DataBaseModule {
    @AppScope
    @Binds
    fun bindDataBaseRepository(dataBaseRepositoryImpl: DataBaseRepositoryImpl): DataBaseRepository

    companion object {
        @Provides
        fun provideAppDataBase(context: Context) = AppDatabase.getInstance(context)
    }
}