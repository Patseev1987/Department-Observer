package ru.bogdan.machine_list.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import di.ViewModelKey
import ru.bogdan.machine_list.ui.MachineListViewModel

@Module
interface MachineListViewModelModule {
    @IntoMap
    @ViewModelKey(MachineListViewModel::class)
    @Binds
    fun bindMainScreenViewModel(impl: MachineListViewModel): ViewModel
}