package ru.bogdan.login_feature.di

import dagger.Component
import di.CoreProvider
import di.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [LoginViewModelModule::class],
    dependencies = [CoreProvider::class]
)
interface LoginComponent : CoreProvider {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(coreProvider: CoreProvider): LoginComponent
    }
}