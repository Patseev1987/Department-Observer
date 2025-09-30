package ru.bogdan.core_impl.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.bogdan.core_impl.data.database.dao.TestDao
import ru.bogdan.core_impl.data.database.model.TestEntity


@Database(
    entities = [TestEntity::class],
    exportSchema = false,
    version = 1
)
//@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun testDao(): TestDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_db"
                    )
                        .fallbackToDestructiveMigration(false)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
