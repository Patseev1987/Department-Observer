package ru.bogdan.core_impl.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import di.CoreProvider


@AppScope
@Component(
    modules = [NetworkModule::class, DataStoreModule::class, ResourceManagerModule::class],
)
interface CoreComponent : CoreProvider {

    companion object {
        fun create(appContext: Context): CoreComponent =
            DaggerCoreComponent.factory().create(appContext)
    }

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appContext: Context,
        ): CoreComponent
    }
}