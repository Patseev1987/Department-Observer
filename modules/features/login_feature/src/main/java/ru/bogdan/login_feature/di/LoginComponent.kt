package ru.bogdan.login_feature.di

import dagger.Component
import di.CoreProvider
import di.FeatureScope
import di.ViewModelFactory

@FeatureScope
@Component(
    modules = [LoginViewModelModule::class],
    dependencies = [CoreProvider::class]
)
interface LoginComponent {

    fun getViewModelFactory(): ViewModelFactory

    companion object {
        fun create(coreProvider: CoreProvider): LoginComponent =
            DaggerLoginComponent.factory().create(coreProvider)
    }

    @Component.Factory
    interface Factory {
        fun create(coreProvider: CoreProvider): LoginComponent
    }
}