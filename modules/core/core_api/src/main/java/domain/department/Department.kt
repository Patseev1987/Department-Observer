package domain.department

import domain.mechanic.Machine

data class Department(
    val id: String,
    val name: String,
    val schema: Schema,
    val machines: List<Machine>,
)
