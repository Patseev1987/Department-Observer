package ru.bogdan.core_impl.data.network.mapers

import domain.info.Info
import domain.mechanic.Machine
import domain.mechanic.MachineDocument
import domain.mechanic.Oil
import domain.mechanic.PartOfMachine
import domain.user.User
import ru.bogdan.core_impl.data.network.models.info.InfoWeb
import ru.bogdan.core_impl.data.network.models.mechanic.MachineDocumentWeb
import ru.bogdan.core_impl.data.network.models.mechanic.MachineWeb
import ru.bogdan.core_impl.data.network.models.mechanic.OilWeb
import ru.bogdan.core_impl.data.network.models.mechanic.PartOfMachineWeb
import ru.bogdan.core_impl.data.network.models.user.UserWeb

class MapperWeb {
    fun machineDocToWeb(doc: MachineDocument) = MachineDocumentWeb(
        id = doc.id,
        name = doc.name,
        description = doc.description,
        machineModel = doc.machineModel
    )

    fun machineDocFromWeb(doc: MachineDocumentWeb) = MachineDocument(
        id = doc.id,
        name = doc.name,
        description = doc.description,
        machineModel = doc.machineModel
    )

    fun partOfMachineFromWeb(part: PartOfMachineWeb) = PartOfMachine(
        id = part.id,
        name = part.name,
        description = part.description
    )

    fun partOfMachineToWeb(part: PartOfMachine) = PartOfMachineWeb(
        id = part.id,
        name = part.name,
        description = part.description
    )

    fun oilToWeb(oil: Oil) = OilWeb(
        id = oil.id,
        name = oil.name,
        description = oil.description,
        imageUrl = oil.imageUrl
    )

    fun oilsFromWeb(oil: OilWeb) = Oil(
        id = oil.id,
        name = oil.name,
        description = oil.description,
        imageUrl = oil.imageUrl
    )

    fun machineToWeb(machine: Machine) = MachineWeb(
        id = machine.id,
        name = machine.name,
        imageUrl = machine.imageUrl,
        description = machine.description,
        model = machine.model,
        state = machine.state,
        type = machine.type,
        yearOfManufacture = machine.yearOfManufacture,
        parts = machine.parts.map { partOfMachineToWeb(it.key) to it.value }.toMap(),
        docs = machine.docs.map { machineDocToWeb(it) },
        oils = machine.oils.map { oilToWeb(it.key) to it.value }.toMap(),
    )

    fun machineFromWeb(machine: MachineWeb) = Machine(
        id = machine.id,
        name = machine.name,
        imageUrl = machine.imageUrl,
        description = machine.description,
        state = machine.state,
        type = machine.type,
        yearOfManufacture = machine.yearOfManufacture,
        parts = machine.parts.map { partOfMachineFromWeb(it.key) to it.value }.toMap(),
        docs = machine.docs.map { machineDocFromWeb(it) },
        oils = machine.oils.map { oilsFromWeb(it.key) to it.value }.toMap(),
        model = machine.model,
    )

    fun userToWeb(user: User) = UserWeb(
        id = user.id,
        name = user.name,
        surname = user.surname,
        patronymic = user.patronymic,
        photoUrl = user.photoUrl,
        role = user.role,
        accessToken = user.accessToken,
        refreshToken = user.refreshToken
    )

    fun userFromWeb(user: UserWeb) = User(
        id = user.id,
        name = user.name,
        surname = user.surname,
        patronymic = user.patronymic,
        photoUrl = user.photoUrl,
        role = user.role,
        accessToken = user.accessToken,
        refreshToken = user.refreshToken
    )

    fun infoFromWeb(info: InfoWeb) = Info(
        id = info.id,
        name = info.name,
        info = info.info,
    )
}