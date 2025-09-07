package ru.bogdan.main_screen_feature.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import di.ViewModelKey
import ru.bogdan.main_screen_feature.ui.HomeScreenViewModel

@Module
interface HomeScreenViewModelModule {
    @IntoMap
    @ViewModelKey(HomeScreenViewModel::class)
    @Binds
    fun bindMainScreenViewModel(impl: HomeScreenViewModel): ViewModel
}