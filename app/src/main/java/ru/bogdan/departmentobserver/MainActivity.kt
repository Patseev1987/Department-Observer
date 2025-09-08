package ru.bogdan.departmentobserver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import navigation.NavigationEvent
import ru.bogdan.departmentobserver.navigation.AppNavGraph
import ru.bogdan.login_feature.ui.LoginScreen
import ru.bogdan.main_screen_feature.ui.MainScreen
import ui.theme.DepartmentObserverTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            DepartmentObserverTheme {
                Scaffold {
                    AppNavGraph(
                        navController
                    )
                }
            }
        }
    }
}
