package ru.bogdan.departmentobserver

import android.app.Application
import di.CoreProvider
import di.DependenciesProvider
import ru.bogdan.core_factory.CoreProviderFactory
import ru.bogdan.departmentobserver.di.DaggerAppComponent

class ObserverApp : Application(), DependenciesProvider {

    override fun onCreate() {
        super.onCreate()
        getCoreProvider()
    }

    override fun getCoreProvider(): CoreProvider {
        return coreProvider ?: DaggerAppComponent
            .factory()
            .create(CoreProviderFactory.createCoreProvider(this))
            .also { coreProvider = it }
    }

    companion object {
        var coreProvider: CoreProvider? = null
    }

}