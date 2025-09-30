package ru.bogdan.core_impl.data.database

import data.DataBaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.bogdan.core_impl.data.database.model.TestEntity
import javax.inject.Inject


class DataBaseRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataBaseRepository {

    private val testDao = database.testDao()
    override suspend fun saveTestString(value: String) {
        withContext(dispatcher) {
            testDao.insert(TestEntity(value = value))
        }
    }

    override val getTestString: Flow<String>
        get() = testDao.getTestString().map { it.value }
}