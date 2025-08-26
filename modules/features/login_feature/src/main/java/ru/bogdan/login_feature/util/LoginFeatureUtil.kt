package ru.bogdan.login_feature.util

import CoreProviderFactory
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import ru.bogdan.login_feature.di.DaggerLoginComponent
import ru.bogdan.login_feature.di.LoginComponent

@Composable
fun getLoginComponent(): LoginComponent {
    val context = LocalContext.current
    return remember {
        DaggerLoginComponent.factory().create(CoreProviderFactory.create(context))
    }
}