package domain.department

import kotlinx.serialization.Serializable

@Serializable
enum class Department(name: String) {
    MAIN_PART_WAREHOUSE("Main warehouse of parts"),
    MAIN_OILS_WAREHOUSE("Main warehouse of oils"),
    UTILIZATION("Utilization"),
    DEPARTMENT_1("Department 1"),
    DEPARTMENT_2("Department 2"),
    DEPARTMENT_3("Department 3")
}