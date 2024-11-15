package com.example.shopease.data.source.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


const val USER_PREFERENCE_NAME = "user_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCE_NAME)
class UserDataStore @Inject constructor(private val dataStore: DataStore<Preferences>)  {

    suspend fun putToken(token: String){
        dataStore.edit {
            it[TOKEN] = token
        }
    }
    suspend fun saveCategory(category: String){
        dataStore.edit {
            it[CATEGORY] = category
        }
    }

    suspend fun saveStart(start: Boolean){
        dataStore.edit {
            it[START] = start
        }
    }

    fun getStart(): Boolean{
        return runBlocking(Dispatchers.IO) {
            dataStore.data.first()[START] ?: false
        }
    }

    fun getCategory(): String? {
        return runBlocking(Dispatchers.IO) {
            dataStore.data.first()[CATEGORY]
        }
    }

     fun getToken(): String? {
         return runBlocking(Dispatchers.IO) {
             dataStore.data.first()[TOKEN]
         }
    }

    suspend fun logout(){
        dataStore.edit {
            it.remove(TOKEN)
        }
    }

    companion object{
        val TOKEN = stringPreferencesKey("token")
        val CATEGORY = stringPreferencesKey("category")
        val START = booleanPreferencesKey("start")


    }
}