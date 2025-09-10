package ru.bogdan.machine_list.di

import dagger.Component
import di.CoreProvider
import di.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [MachineListViewModelModule::class],
    dependencies = [CoreProvider::class]
)
interface MachinesListComponent : CoreProvider {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(coreProvider: CoreProvider): MachinesListComponent
    }
}
