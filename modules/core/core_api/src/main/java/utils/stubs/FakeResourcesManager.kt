package utils.stubs

import android.graphics.drawable.Drawable
import data.ResourceManager

object FakeResourcesManager : ResourceManager {
    override fun getString(id: Int): String {
        return "Working"
    }

    override fun getString(id: Int, vararg args: Any): String {
        return "Hello String with args " + args[0]
    }

    override fun getStringArray(id: Int): Array<String> {
        return arrayOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9",
        )
    }

    override fun getDrawable(id: Int): Drawable? {
        return null
    }

    override fun getColor(id: Int): Int {
        return 0
    }
}