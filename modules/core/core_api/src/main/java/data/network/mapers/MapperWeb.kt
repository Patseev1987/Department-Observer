package data.network.mapers

import data.network.models.mechanic.MachineDocumentWeb
import data.network.models.mechanic.MachineWeb
import data.network.models.mechanic.PartOfMachineWeb
import domain.department.MyPoint
import domain.mechanic.Machine
import domain.mechanic.MachineDocument
import domain.mechanic.MachineState
import domain.mechanic.MachineType
import domain.mechanic.PartOfMachine
import kotlin.String

class MapperWeb {
    fun machineDocToWeb(doc: MachineDocument) = MachineDocumentWeb(
        id = doc.id,
        name = doc.name,
        description = doc.description,
        machineId = doc.machineId
    )

    fun machineDocFromWeb(doc: MachineDocumentWeb) = MachineDocument(
        id = doc.id,
        name = doc.name,
        description = doc.description,
        machineId = doc.machineId
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

    fun machineToWeb(machine: Machine) = MachineWeb(
        id = machine.id,
    name = machine.name,
    imageUrl = machine.imageUrl,
    description = machine.description,
    state = machine.state,
    type = machine.type,
    yearOfManufacture = machine.yearOfManufacture,
    parts = machine.parts.map{ partOfMachineToWeb(it)},
    docs = machine.docs.map{ machineDocToWeb(it)},
    oils = machine.oils,
    offset = machine.offset,
    rotateAngle = machine.rotateAngle,
    pointsForPath = machine.pointsForPath
    )
}