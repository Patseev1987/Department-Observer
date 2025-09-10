package ru.bogdan.machine_list.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import di.ViewModelKey
import ru.bogdan.machine_list.ui.machineDetails.MachineDetailsViewModel

@Module
interface MachineDetailsViewModelModule {
    @IntoMap
    @ViewModelKey(MachineDetailsViewModel::class)
    @Binds
    fun bindMainScreenViewModel(impl: MachineDetailsViewModel): ViewModel
}