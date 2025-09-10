package data.resorseMenager

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import javax.inject.Inject

class ResourceManagerImpl @Inject constructor(private val context: Context) :
    ResourceManager {
    override fun getString(id: Int): String = context.getString(id)
    override fun getString(id: Int, vararg args: Any): String = context.getString(id, *args)
    override fun getStringArray(id: Int): Array<String> = context.resources.getStringArray(id)
    override fun getDrawable(id: Int): Drawable? = ContextCompat.getDrawable(context, id)

    override fun getColor(id: Int): Int = context.getColor(id)
}