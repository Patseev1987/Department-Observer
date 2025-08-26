package ru.bogdan.departmentobserver

import CoreProviderFactory
import android.app.Application
import di.CoreProvider
import ru.bogdan.departmentobserver.di.AppComponent
import ru.bogdan.departmentobserver.di.DaggerAppComponent
import ru.bogdan.login_feature.di.DaggerLoginComponent

class ObserverApp : Application() {
    private val coreProvider:CoreProvider by lazy {
       CoreProviderFactory.create(this)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreProvider)
    }

    val loginComponent by lazy {
        DaggerLoginComponent.factory().create(coreProvider)
    }




}