package ru.bogdan.departmentobserver.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import navigation.NavigationEvent
import ru.bogdan.login_feature.ui.LoginScreen
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreen


@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(navHostController, startDestination = NavigationEvent.LoginScreen, modifier = modifier) {
        composable<NavigationEvent.LoginScreen> {
            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateEvent = { event ->
                    navHostController.navigate(event)
                }
            )
        }

        navigation<NavigationEvent.Main>(startDestination = NavigationEvent.Home()) {
            composable<NavigationEvent.Home> {
                val id = it.toRoute<NavigationEvent.Home>().userId
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    userId = id,
                )
            }
        }
    }
}