package data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_table")
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val value: String,
)
