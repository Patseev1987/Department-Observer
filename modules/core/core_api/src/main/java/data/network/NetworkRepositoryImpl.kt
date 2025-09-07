package data.network

import domain.mechanic.Machine
import domain.user.User
import java.security.MessageDigest
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val client: NetWorkClientApplication,
    ) : NetworkRepository {
    override suspend fun getTestData(): String {
        return client.getText()
    }

    override suspend fun login(login: String, password: String): Result<User> {
        val md5 = MessageDigest.getInstance("MD5")
        val bytes = md5.digest(password.toByteArray())
        val passwordHash = bytes.toHexString()
      return runCatching { client.login(login, passwordHash) }
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshToken(): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: String): Result<User> {
        return runCatching { client.getUserById(userId) }
    }

    override suspend fun getMachines(): Result<List<Machine>> {
        return runCatching { client.getMachines() }
    }
}