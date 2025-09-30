package ru.bogdan.departmentobserver.di

import dagger.Component
import di.CoreProvider
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [CoreProvider::class],
)
interface AppComponent : CoreProvider {

    companion object {
        fun create(coreProvider: CoreProvider): AppComponent =
            DaggerAppComponent.factory().create(coreProvider = coreProvider)
    }


    @Component.Factory
    interface Factory {
        fun create(
            coreProvider: CoreProvider
        ): AppComponent
    }
}