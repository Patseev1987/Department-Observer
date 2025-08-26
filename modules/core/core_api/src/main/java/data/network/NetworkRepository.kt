package data.network

import domain.user.User

interface NetworkRepository {

   suspend fun getTestData(): String

   suspend fun login(login: String, password: String): User
}

