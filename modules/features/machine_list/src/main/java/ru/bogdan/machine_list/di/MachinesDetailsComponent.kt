package ru.bogdan.machine_list.di

import dagger.BindsInstance
import dagger.Component
import di.CoreProvider
import di.FeatureScope
import di.ViewModelFactory

@FeatureScope
@Component(
    modules = [MachineDetailsViewModelModule::class],
    dependencies = [CoreProvider::class]
)
interface MachinesDetailsComponent {

    fun getViewModelFactory(): ViewModelFactory

    companion object {
        fun create(coreProvider: CoreProvider, @MachineId machineId: String): MachinesDetailsComponent =
            DaggerMachinesDetailsComponent.factory().create(coreProvider, machineId)
    }

    @Component.Factory
    interface Factory {
        fun create(coreProvider: CoreProvider, @BindsInstance @MachineId machineId: String): MachinesDetailsComponent
    }
}
