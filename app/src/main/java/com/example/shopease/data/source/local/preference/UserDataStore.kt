package com.example.shopease.data.source.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


const val USER_PREFERENCE_NAME = "user_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCE_NAME)
class UserDataStore @Inject constructor(private val dataStore: DataStore<Preferences>)  {

    suspend fun saveLogin(isLogin: Boolean){
        dataStore.edit {
            it[LOGIN] = isLogin
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

    fun clearCategory(){
        runBlocking(Dispatchers.IO) {
            dataStore.edit {
                it.remove(CATEGORY)
            }
        }
    }

     fun getLogin(): Boolean {
         return runBlocking(Dispatchers.IO) {
             dataStore.data.first()[LOGIN] ?: false
         }
    }

    suspend fun logout(){
        dataStore.edit {
            it[LOGIN] = false
        }
    }

    companion object{
        val CATEGORY = stringPreferencesKey("category")
        val START = booleanPreferencesKey("start")
        val LOGIN = booleanPreferencesKey("login")


    }
}