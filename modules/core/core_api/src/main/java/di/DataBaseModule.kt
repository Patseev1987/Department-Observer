package di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import data.database.AppDatabase
import data.database.DataBaseRepository
import data.database.DataBaseRepositoryImpl

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