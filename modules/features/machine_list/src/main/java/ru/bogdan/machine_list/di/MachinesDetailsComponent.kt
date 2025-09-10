package ru.bogdan.machine_list.di

import dagger.BindsInstance
import dagger.Component
import di.CoreProvider
import di.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [MachineDetailsViewModelModule::class],
    dependencies = [CoreProvider::class]
)
interface MachinesDetailsComponent : CoreProvider {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(coreProvider: CoreProvider, @BindsInstance @MachineId machineId: String): MachinesDetailsComponent
    }
}
