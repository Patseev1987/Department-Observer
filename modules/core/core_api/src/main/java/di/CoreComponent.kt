package di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [NetworkModule::class, DataStoreModule::class, DataBaseModule::class, ResourceManagerModule::class],
)
interface CoreComponent : CoreProvider {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appContext: Context,
        ): CoreComponent
    }
}