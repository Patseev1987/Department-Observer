package ru.bogdan.departmentobserver


import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import ru.bogdan.core_ui.ui.theme.DepartmentObserverTheme
import ru.bogdan.departmentobserver.ui.Application

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.WHITE
            )
        )

        setContent {
            DepartmentObserverTheme {
                Application(Modifier.fillMaxSize())
            }
        }
    }
}
