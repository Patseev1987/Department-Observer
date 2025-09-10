package ru.bogdan.departmentobserver.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import navigation.NavigationEvent
import ru.bogdan.login_feature.ui.LoginScreen
import ru.bogdan.machine_list.ui.MachineList
import ru.bogdan.machine_list.ui.machineDetails.MachineDetails
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreen


@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {

    NavHost(navHostController, startDestination = NavigationEvent.LoginScreen, modifier = modifier) {
        composable<NavigationEvent.LoginScreen> {
            LoginScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                onNavigateEvent = { event ->
                    navHostController.navigate(event){
                        launchSingleTop = true
                        popUpTo(NavigationEvent.LoginScreen::class) { inclusive = true }
                    }
                }
            )
        }

        machineGraph(
            navHostController = navHostController,
            paddingValues = paddingValues,
        )

        composable<NavigationEvent.Home> {
            HomeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            )
        }
    }
}