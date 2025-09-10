package ru.bogdan.main_screen_feature.di

import dagger.BindsInstance
import dagger.Component
import di.CoreProvider
import di.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [HomeScreenViewModelModule::class],
    dependencies = [CoreProvider::class]
)
interface HomeScreenComponent : CoreProvider {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(coreProvider: CoreProvider): HomeScreenComponent
    }
}
