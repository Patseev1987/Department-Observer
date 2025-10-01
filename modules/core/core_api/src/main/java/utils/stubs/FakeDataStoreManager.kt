package utils.stubs

import data.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeDataStoreManager: DataStoreManager {
    override val accessToken: Flow<String?>
        get() = flow{
            emit("112233")
        }
    override val refreshToken: Flow<String?>
        get() = flow{
            emit("445566")
        }
    override val userId: Flow<String?>
        get() = flow{
            emit("user1")
        }

    override suspend fun saveAccessTokens(
        accessToken: String?,
        refreshToken: String?,
        userId: String?
    ) {
        println("saveAccessTokens")
    }

    override suspend fun clear() {
        println("clear")
    }
}