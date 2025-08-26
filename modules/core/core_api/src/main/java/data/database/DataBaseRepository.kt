package data.database

import kotlinx.coroutines.flow.Flow

interface DataBaseRepository {
    suspend fun saveTestString(value: String)

    val getTestString: Flow<String>
}