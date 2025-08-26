package data.dataStore

import android.os.Build
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

    val accessToken: Flow<String?>

    val refreshToken: Flow<String?>

    suspend fun saveAccessTokens(accessToken: String?, refreshToken: String?)

    suspend fun clear()

    companion object {
        const val dataStoreFile: String = "securePref"
        const val KEY_PROVIDER = "AndroidKeyStore"
        const val AES_ALGORITHM = "AES"
        const val AES_KEY_ALIAS = "AES_ALIAS"
        const val AES_MODE = "AES/CBC/PKCS7Padding"

        fun generateIv(): ByteArray {
            val result = ByteArray(16)
            val deviceInfo = buildString {
                append(Build.ID)
                append(Build.MANUFACTURER)
                append(Build.MODEL)
                append(Build.PRODUCT)
                append(Build.DEVICE)
                append(Build.DISPLAY)
            }.toByteArray()
            System.arraycopy(deviceInfo, 0, result, 0, 16)
            return result
        }
    }
}