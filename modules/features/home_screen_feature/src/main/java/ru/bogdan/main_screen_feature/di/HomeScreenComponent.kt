package ru.bogdan.main_screen_feature.di

import dagger.Component
import di.CoreProvider
import di.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [HomeScreenViewModelModule::class],
    dependencies = [CoreProvider::class]
)
interface HomeScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    companion object {
        fun create(coreProvider: CoreProvider): HomeScreenComponent =
            DaggerHomeScreenComponent.factory().create(coreProvider)
    }

    @Component.Factory
    interface Factory {
        fun create(coreProvider: CoreProvider): HomeScreenComponent
    }
}
