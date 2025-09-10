package data.network

import domain.info.Residue
import domain.mechanic.Machine
import domain.user.User

interface NetworkRepository {

    suspend fun getTestData(): String

    suspend fun login(login: String, password: String): Result<User>

    suspend fun logout()

    suspend fun refreshToken(): Result<User>

    suspend fun getUserById(userId: String): Result<User>

    suspend fun getMachines(): Result<List<Machine>>

    //suspend fun getRecords(): Result<Records>

    suspend fun getImportantInfo(userId: String, lessThan: Int): Result<List<Residue>>
}

