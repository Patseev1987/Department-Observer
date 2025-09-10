package ru.bogdan.departmentobserver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold


import androidx.navigation.compose.rememberNavController
import ru.bogdan.departmentobserver.navigation.AppNavGraph
import ru.bogdan.core_ui.ui.theme.DepartmentObserverTheme

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
