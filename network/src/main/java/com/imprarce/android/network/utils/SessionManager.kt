package com.imprarce.android.network.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.imprarce.android.network.utils.Constants.EMAIL_KEY
import com.imprarce.android.network.utils.Constants.ID_KEY
import com.imprarce.android.network.utils.Constants.JWT_TOKEN_KEY
import kotlinx.coroutines.flow.first

class SessionManager(val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("session_manager")

    suspend fun updateSession(token: String, email: String){
        val jwtTokenKey = stringPreferencesKey(JWT_TOKEN_KEY)
        val emailKey = stringPreferencesKey(EMAIL_KEY)
        context.dataStore.edit { preferences ->
            preferences[jwtTokenKey] = token
            preferences[emailKey] = email
        }
    }


    suspend fun saveJwtToken(token: String){
        val jwtTokenKey = stringPreferencesKey(JWT_TOKEN_KEY)
        context.dataStore.edit { preferences ->
            preferences[jwtTokenKey] = token
        }
    }

    suspend fun getJwtToken(): String?{
        val jwtTokenKey = stringPreferencesKey(JWT_TOKEN_KEY)
        val preferences = context.dataStore.data.first()
        return preferences[jwtTokenKey]
    }

    suspend fun getCurrentUserEmail(): String?{
        val emailKey = stringPreferencesKey(EMAIL_KEY)
        val preferences = context.dataStore.data.first()
        return preferences[emailKey]
    }

    suspend fun setCurrentUserId(id: Int){
        val idKey = intPreferencesKey(ID_KEY)
        context.dataStore.edit { preferences ->
            preferences[idKey] = id
        }
    }

    suspend fun getCurrentUserId(): Int?{
        val idKey = intPreferencesKey(ID_KEY)
        val preferences = context.dataStore.data.first()
        return preferences[idKey]
    }

    suspend fun logout(){
        context.dataStore.edit {
            it.clear()
        }
    }

}