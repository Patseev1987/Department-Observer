package ru.bogdan.departmentobserver.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import navigation.NavigationEvent
import ru.bogdan.machine_list.ui.MachineList
import ru.bogdan.machine_list.ui.machineDetails.MachineDetails

fun NavGraphBuilder.machineGraph(
    navHostController: NavController,
    paddingValues: PaddingValues,
) {
    navigation<NavigationEvent.MachineMain>(startDestination = NavigationEvent.MachinesScreen) {
        composable<NavigationEvent.MachinesScreen> {
            MachineList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                onMachineClick = {
                    val id = it.machineId
                    navHostController.navigate(NavigationEvent.MachineScreen(id)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavigationEvent.MachineScreen> {
            val id = it.toRoute<NavigationEvent.MachineScreen>().machineId
            MachineDetails(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                machineId = id,
            )
        }
    }
}