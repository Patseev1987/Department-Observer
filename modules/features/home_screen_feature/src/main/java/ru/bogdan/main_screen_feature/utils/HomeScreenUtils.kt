package ru.bogdan.main_screen_feature.utils

import CoreProviderFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import ru.bogdan.main_screen_feature.di.DaggerHomeScreenComponent
import ru.bogdan.main_screen_feature.di.HomeScreenComponent

@Composable
fun getHomeScreenComponent(): HomeScreenComponent {
    val context = LocalContext.current
    return remember {
        DaggerHomeScreenComponent.factory().create(CoreProviderFactory.create(context))
    }
}