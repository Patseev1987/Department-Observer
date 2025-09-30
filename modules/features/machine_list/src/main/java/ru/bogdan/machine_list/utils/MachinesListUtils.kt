package ru.bogdan.machine_list.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import data.ResourceManager
import di.DependenciesProvider
import domain.mechanic.MachineState
import domain.mechanic.MachineType
import ru.bogdan.core_ui.R
import ru.bogdan.machine_list.di.MachinesDetailsComponent
import ru.bogdan.machine_list.di.MachinesListComponent

@Composable
fun getMachineListComponent(): MachinesListComponent {
    val coreProvider = (LocalContext.current.applicationContext as DependenciesProvider).getCoreProvider()
    return remember { MachinesListComponent.create(coreProvider) }
}

@Composable
fun getMachineDetailsComponent(machineId: String): MachinesDetailsComponent {
    val coreProvider = (LocalContext.current.applicationContext as DependenciesProvider).getCoreProvider()
    return remember { MachinesDetailsComponent.create(coreProvider, machineId) }
}

fun getTypeDescriptions(resourceManager: ResourceManager): List<String> {
    val tempList = mutableListOf<String>()
    MachineType.entries.forEach { type ->
        when (type) {
            MachineType.TURNING -> {
                tempList.add(resourceManager.getString(R.string.turning))
            }

            MachineType.TURNING_CNC -> {
                tempList.add(resourceManager.getString(R.string.turning_cnc))
            }

            MachineType.MILLING_HORIZONTAL -> {
                tempList.add(resourceManager.getString(R.string.milling_horizontal))
            }

            MachineType.MILLING_UNIVERSAL -> {
                tempList.add(resourceManager.getString(R.string.milling_universal))
            }

            MachineType.MILLING_CNC -> {
                tempList.add(resourceManager.getString(R.string.milling_cnc))
            }
        }
    }
    return tempList
}

fun MachineType.getTypeDescription(resourceManager: ResourceManager): String {
    return when (this) {
        MachineType.TURNING -> {
            resourceManager.getString(R.string.turning)
        }

        MachineType.TURNING_CNC -> {
            resourceManager.getString(R.string.turning_cnc)
        }

        MachineType.MILLING_HORIZONTAL -> {
            resourceManager.getString(R.string.milling_horizontal)
        }

        MachineType.MILLING_UNIVERSAL -> {
            resourceManager.getString(R.string.milling_universal)
        }

        MachineType.MILLING_CNC -> {
            resourceManager.getString(R.string.milling_cnc)
        }
    }
}

fun getStateDescriptions(resourceManager: ResourceManager): List<String> {
    val tempList = mutableListOf<String>()
    MachineState.entries.forEach { state ->
        when (state) {
            MachineState.WORKING -> {
                tempList.add(resourceManager.getString(R.string.working))
            }

            MachineState.REPAIR -> {
                tempList.add(resourceManager.getString(R.string.repair))
            }

            MachineState.STOPPED -> {
                tempList.add(resourceManager.getString(R.string.pause))
            }
        }
    }
    return tempList
}

fun MachineState.getStateDescription(resourceManager: ResourceManager): String {
    return when (this) {
        MachineState.WORKING -> {
            resourceManager.getString(R.string.working)
        }

        MachineState.REPAIR -> {
            resourceManager.getString(R.string.repair)
        }

        MachineState.STOPPED -> {
            resourceManager.getString(R.string.pause)
        }
    }
}