package data.network

import domain.user.User
import java.security.MessageDigest
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val client: NetWorkClientApplication,
    ) : NetworkRepository {
    override suspend fun getTestData(): String {
        return client.getText()
    }

    override suspend fun login(login: String, password: String): User {
        val md5 = MessageDigest.getInstance("MD5")
        val bytes = md5.digest(password.toByteArray())
        val passwordHash = bytes.toHexString()
      return  client.login(login, passwordHash)
    }
}