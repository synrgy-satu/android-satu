package com.example.satu.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.satu.data.model.response.auth.DataUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_user")
class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val userTokenKey = stringPreferencesKey(USER_TOKEN_KEY)
    private val refreshTokenKey = stringPreferencesKey(REFRESH_TOKEN_KEY)
    fun getSession(): Flow<DataUser> = dataStore.data.map {
        DataUser(
            accessToken = it[userTokenKey] ?: "",
            refreshToken = it[refreshTokenKey] ?: ""
        )
    }
    suspend fun saveSession(data: DataUser) = dataStore.edit {
        with(it) {
            this[userTokenKey] = data.accessToken
            this[refreshTokenKey] = data.refreshToken
        }
    }
    suspend fun deleteSession() = dataStore.edit { it.clear() }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
        private const val USER_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }
}