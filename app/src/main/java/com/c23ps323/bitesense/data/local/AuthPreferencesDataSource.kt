package com.c23ps323.bitesense.data.local



import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AuthPreferencesDataSource  constructor(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) {

    /**
     * Get user's authentication token
     *
     * @return Flow
     */
    fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    /**
     * Save the user's authentication token to preferences
     *
     * @param token Authentication token
     */
    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token_data")
    }
}