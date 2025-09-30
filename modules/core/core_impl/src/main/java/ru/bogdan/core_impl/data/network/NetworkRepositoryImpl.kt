package ru.bogdan.core_impl.data.network

import data.NetworkRepository
import domain.info.Info
import domain.mechanic.Machine
import domain.user.User
import ru.bogdan.core_impl.data.network.mapers.MapperWeb
import java.security.MessageDigest
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val client: NetWorkClientApplication,
    private val mapperWeb: MapperWeb
) : NetworkRepository {

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
        return runCatching {
            mapperWeb.userFromWeb(
                client.getUserById(userId)
            )
        }
    }

    override suspend fun getMachines(): Result<List<Machine>> {
        return runCatching {
            client.getMachines().map { mapperWeb.machineFromWeb(it) }
        }
    }

    override suspend fun getMachineById(id: String): Result<Machine> {
        return runCatching {
            mapperWeb.machineFromWeb(client.getMachineById(id))
        }
    }

    override suspend fun changeState(machine: Machine) {
        client.changeState(mapperWeb.machineToWeb(machine))
    }

    override suspend fun downloadDocById(docId: String): Result<ByteArray> {
        return runCatching {
            client.getDocById(docId)
        }
    }

    override suspend fun getInfo(): Result<List<Info>> {
        return runCatching {
            client.getInfo().map {
                mapperWeb.infoFromWeb(it)
            }
        }
    }
}