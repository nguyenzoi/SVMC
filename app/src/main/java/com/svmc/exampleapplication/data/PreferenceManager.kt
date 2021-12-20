package com.svmc.exampleapplication.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "PreferenceManager"
enum class Order {
    SORT_BY_NAME, SORT_BY_DATE
}

data class FilterPreference(val order: Order, val hideCompleted: Boolean)

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.createDataStore("user_preference")

    val dataStoreFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d(TAG, "datastore ioexception ")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val order = Order.valueOf(
                preferences[PreferenceKeys.orderBy] ?: Order.SORT_BY_NAME.name
            )
            val hideCompleted = preferences[PreferenceKeys.hideCompleted] ?: false

            FilterPreference(order = order, hideCompleted = hideCompleted)
        }

    suspend fun updateSortBy (sortBy: Order) =
        dataStore.edit {preferences ->
            preferences[PreferenceKeys.orderBy] = sortBy.name
        }

    suspend fun updateHideCompleted (status: Boolean) =
        dataStore.edit {preferences ->
            preferences[PreferenceKeys.hideCompleted] = status
        }


    private object PreferenceKeys {
        val orderBy = preferencesKey<String>("order_by")
        val hideCompleted = preferencesKey<Boolean>("hide_completed")
    }
}