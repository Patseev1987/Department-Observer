package ru.bogdan.machine_list.utils

import CoreProviderFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import ru.bogdan.machine_list.di.DaggerMachinesListComponent
import ru.bogdan.machine_list.di.MachinesListComponent

@Composable
fun getMachineLsListComponent(): MachinesListComponent {
    val context = LocalContext.current
   return remember {  DaggerMachinesListComponent.factory().create(CoreProviderFactory.create(context)) }
}