package data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.database.model.TestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    @Insert
    suspend fun insert(testEntity: TestEntity)

    @Query("SELECT * FROM `test_table`")
    fun getTestString(): Flow<TestEntity>
}