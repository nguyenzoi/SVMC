package com.svmc.exampleapplication.luantv.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferenceManager"

enum class Order {
    BY_DATE, BY_NAME
}

data class FilterPreferences (val sortOrder: Order, val hideCompleted: Boolean)

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext  private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("task_preference")

    val preferenceFlow = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading references: ")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortOrder = Order.valueOf(
                preferences[PreferenceKeys.SORT_ORDER] ?: Order.BY_NAME.name
            )
            val hideCompleted = preferences[PreferenceKeys.HIDE_COMPLETED] ?: false
            FilterPreferences(sortOrder, hideCompleted)
        }

    suspend fun updateSortOder (sortOrder: Order) {
        context.dataStore.edit {preference ->
            preference[PreferenceKeys.SORT_ORDER] = sortOrder.name
        }
    }

    suspend fun updateHideCompleted (hideCompleted: Boolean) {
        context.dataStore.edit {preference ->
            preference[PreferenceKeys.HIDE_COMPLETED] = hideCompleted
        }
    }

    object PreferenceKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val HIDE_COMPLETED = booleanPreferencesKey("hide_completed")
    }

}