package ru.bogdan.core_impl

import domain.info.Info
import domain.mechanic.Machine
import ru.bogdan.core_impl.data.network.models.info.InfoWeb
import ru.bogdan.core_impl.data.network.models.mechanic.MachineWeb

object RepositoryDataFactory {
    fun getMachines() = listOf(
        Machine.NONE,
        Machine.NONE.copy(id = "777"),
    )

    fun getMachinesWeb() = listOf(
        MachineWeb.NONE,
        MachineWeb.NONE.copy(id = "777"),
    )

    val byteArray = "Hello World".toByteArray()

    fun getInfoWeb() = listOf(
        InfoWeb("1", "a", info[0]),
        InfoWeb("2", "b", info[1]),
        InfoWeb("3", "c", info[2]),
    )

    fun getInfo() = listOf(
        Info("1", "a", info[0]),
        Info("2", "b", info[1]),
        Info("3", "c", info[2]),
    )

  private  val info = listOf(
        "1. Helo",
        "2. GoodBay",
        "3. Welcome"
    )
}