package ru.bogdan.main_screen_feature.ui.homeScreen

sealed interface HomeScreenIntent {
    data class ShowRepairList(val isShow: Boolean) : HomeScreenIntent
    data class ShowInfoList(val isShow: Boolean) : HomeScreenIntent
}