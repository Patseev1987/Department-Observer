import android.content.Context
import di.CoreProvider
import di.DaggerCoreComponent

object CoreProviderFactory {
    fun create(context: Context): CoreProvider = DaggerCoreComponent.factory().create(context)
}