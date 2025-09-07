package domain.mechanic

import domain.department.MyPoint

data class Machine(
    val id: String,
    val name: String,
    val description: String,
    val state: MachineState,
    val idDepartment: String,
    val type: MachineType,
    val parts: List<PartOfMachine>,
    val docs: List<MachineDocument>,
    val offset: MyPoint,
    val pointsForPath: List<MyPoint>,
) {

}
