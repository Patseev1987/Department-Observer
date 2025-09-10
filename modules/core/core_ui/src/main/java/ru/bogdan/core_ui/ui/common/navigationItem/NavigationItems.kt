package ru.bogdan.core_ui.ui.common.navigationItem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.bogdan.core_ui.R

@Composable
fun getNavigationItems(): List<ObserverNavigationItem> {
    return remember {
        listOf(
            ObserverNavigationItem.HomeItem(
                titleId = R.string.home_item,
                drawableId = R.drawable.home_screen
            ),
            ObserverNavigationItem.MachinesItem(
                titleId = R.string.machines_item,
                drawableId = R.drawable.machines_background
            )
        )
    }
}