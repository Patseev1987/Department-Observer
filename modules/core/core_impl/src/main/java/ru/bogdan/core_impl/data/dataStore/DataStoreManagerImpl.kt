package ru.bogdan.core_impl.data.dataStore

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import data.DataStoreManager
import data.DataStoreManager.Companion.AES_KEY_ALIAS
import data.DataStoreManager.Companion.AES_MODE
import data.DataStoreManager.Companion.KEY_PROVIDER
import data.DataStoreManager.Companion.dataStoreFile
import data.DataStoreManager.Companion.generateIv
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(private val context: Context) : DataStoreManager {

    private val fixedIV by lazy {
        generateIv()
    }

    private val keyStore by lazy {
        KeyStore.getInstance(KEY_PROVIDER).apply {
            load(null)
        }
    }

    private fun generateSecreteKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_PROVIDER)
        val spec = KeyGenParameterSpec.Builder(
            AES_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .setKeySize(256)
            .setRandomizedEncryptionRequired(false)
            .build()
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    private fun getSecreteKey(): SecretKey {
        return keyStore.getKey(AES_KEY_ALIAS, null) as SecretKey? ?: generateSecreteKey()
    }

    private fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getSecreteKey(), IvParameterSpec(fixedIV))
        val encodedBytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encodedBytes, Base64.NO_WRAP)
    }

    fun decrypt(encryptedText: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, getSecreteKey(), IvParameterSpec(fixedIV))
        val encryptedText = Base64.decode(encryptedText, Base64.NO_WRAP)
        val decode = cipher.doFinal(encryptedText)
        return String(decode, Charsets.UTF_8)
    }

    override val accessToken: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            val accessToken = preferences[ACCESS_TOKEN] ?: EMPTY_STRING
            if (accessToken.isNotBlank()) {
                decrypt(accessToken)
            } else EMPTY_STRING
        }
    override val refreshToken: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            val refreshToken = preferences[REFRESH_TOKEN] ?: EMPTY_STRING
            if (refreshToken.isNotBlank()) {
                decrypt(refreshToken)
            } else EMPTY_STRING
        }

    override val userId: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            val userId = preferences[USER_ID] ?: EMPTY_STRING
            if (userId.isNotBlank()) {
                decrypt(userId)
            } else {
                EMPTY_STRING
            }
        }

    override suspend fun saveAccessTokens(accessToken: String?, refreshToken: String?, userId: String?) {
        context.dataStore.edit { preferences ->
            accessToken?.let { preferences[ACCESS_TOKEN] = encrypt(it) }
            refreshToken?.let { preferences[REFRESH_TOKEN] = encrypt(it) }
            userId?.let { preferences[USER_ID] = encrypt(it) }
        }
    }

    override suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val Context.dataStore by preferencesDataStore(name = dataStoreFile)
        private val ACCESS_TOKEN = stringPreferencesKey("key_access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("key_refresh_token")
        private val USER_ID = stringPreferencesKey("user_id")
        private const val EMPTY_STRING = ""
    }
}