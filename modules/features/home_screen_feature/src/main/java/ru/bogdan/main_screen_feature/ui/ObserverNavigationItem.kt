package ru.bogdan.main_screen_feature.ui

import navigation.NavigationEvent

sealed class ObserverNavigationItem(
    val title: String,
    val drawableId: Int
) {
    class MapItem(
        title: String,
        drawableId: Int
    ): ObserverNavigationItem(title,  drawableId)

    class HomeItem(
        title: String,
        drawableId: Int
    ): ObserverNavigationItem(title,  drawableId)

    class WarehousNameItem(
        title: String,
        drawableId: Int
    ): ObserverNavigationItem(title,  drawableId)
}
