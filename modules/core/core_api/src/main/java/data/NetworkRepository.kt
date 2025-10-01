package data

import domain.info.Info
import domain.mechanic.Machine
import domain.user.LoginResponse
import domain.user.User

interface NetworkRepository {

    suspend fun login(login: String, password: String): Result<LoginResponse>

    suspend fun logout()

    suspend fun refreshToken(): Result<User>

    suspend fun getUserById(userId: String): Result<User>

    suspend fun getMachines(): Result<List<Machine>>

    suspend fun getMachineById(id: String): Result<Machine>

    suspend fun changeState(machine: Machine)

    suspend fun downloadDocById(docId: String): Result<ByteArray>

    suspend fun getInfo(): Result<List<Info>>
}

