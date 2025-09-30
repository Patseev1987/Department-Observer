package ru.bogdan.core_factory

import android.content.Context
import di.CoreProvider
import ru.bogdan.core_impl.di.CoreComponent

object CoreProviderFactory {
    fun createCoreProvider(context: Context): CoreProvider = CoreComponent.create(context)
}