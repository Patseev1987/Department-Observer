package data.network

import domain.user.User

interface NetworkRepository {

   suspend fun getTestData(): String

   suspend fun login(login: String, password: String): Result<User>

   suspend fun logout()

   suspend fun refreshToken(): Result<User>

   suspend fun getUserById(userId: String): Result<User>
}

