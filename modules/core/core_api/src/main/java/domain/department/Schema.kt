package domain.department

import domain.mechanic.Machine

data class Schema(
    val id: String,
    val idDepartment: String,
    val walls: List<MyPoint>,
    val machines: List<Machine>,
)
