package ru.bogdan.core_ui.ui.common.navigationItem

import navigation.NavigationEvent


sealed class ObserverNavigationItem(
    val titleId: Int,
    val drawableId: Int,
    val route: NavigationEvent,
) {
    class HomeItem(
        titleId: Int,
        drawableId: Int
    ) : ObserverNavigationItem(titleId, drawableId, NavigationEvent.Home)

//    class WarehousItem(
//        titleId: Int,
//        drawableId: Int
//    ): ObserverNavigationItem(titleId,  drawableId)

    class MachinesItem(
        titleId: Int,
        drawableId: Int
    ) : ObserverNavigationItem(
        titleId,
        drawableId,
        NavigationEvent.MachineMain,
    )
}