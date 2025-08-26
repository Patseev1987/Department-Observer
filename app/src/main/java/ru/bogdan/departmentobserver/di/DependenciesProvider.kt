package ru.bogdan.departmentobserver.di

import di.CoreProvider

interface DependenciesProvider: CoreProvider {
    fun getDependencies(): AppComponent
}