package ru.bogdan.departmentobserver.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import navigation.NavigationEvent
import ru.bogdan.core_ui.ui.common.lightScaffold.LightScaffold
import ru.bogdan.core_ui.ui.common.navigationBars.bottomNavigationBar.BottomNavigationAppBar
import ru.bogdan.core_ui.ui.common.navigationBars.bottomNavigationBar.BoxItemNavigation
import ru.bogdan.core_ui.ui.common.navigationItem.getNavigationItems
import ru.bogdan.core_ui.ui.theme.Emerald
import ru.bogdan.departmentobserver.navigation.AppNavGraph

@Composable
fun Application(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination?.hierarchy
    val itemList = getNavigationItems()
    LightScaffold(
        modifier = modifier,
        bottomBar = {
            if (!(currentDestination?.any { it.hasRoute(NavigationEvent.LoginScreen::class) } ?: false)) {
                BottomNavigationAppBar {
                    itemList.forEach { item ->
                        BoxItemNavigation(
                            isSelected = currentDestination?.any { it.hasRoute(item.route::class) } ?: false,
                            onClick = {
                                if (!(currentDestination?.any { it.hasRoute(item.route::class) } ?: false)) {
                                    navController.navigate(item.route) {
                                        launchSingleTop = true
                                        popUpTo(NavigationEvent.Home::class){
                                            inclusive = false
                                        }
                                    }
                                }
                            },
                            iconId = item.drawableId,
                            label = stringResource(item.titleId),
                            tint = Emerald
                        )
                    }
                }
            }
        },
        content = {
            AppNavGraph(
                navHostController = navController,
                paddingValues = it
            )
        }
    )
}