package ru.bogdan.core_impl.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.bogdan.core_impl.data.database.model.TestEntity


@Dao
interface TestDao {
    @Insert
    suspend fun insert(testEntity: TestEntity)

    @Query("SELECT * FROM `test_table`")
    fun getTestString(): Flow<TestEntity>
}