package com.nguyenle.mvvmtodo.data

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

enum class SortOrder { BY_NAME, BY_DATE }

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.createDataStore("user_preferences")

    val sortOrder = dataStore.data
        .map {
            SortOrder.valueOf(it[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_DATE.name)
        }
    val hideCompleted = dataStore.data.map { it[PreferencesKeys.HIDE_COMPLETED] ?: false }

    suspend fun updateSortOrder(sortOrder: SortOrder) {
        dataStore.edit { it[PreferencesKeys.SORT_ORDER] = sortOrder.name }
    }

    suspend fun updateHideCompleted(hideCompleted: Boolean) {
        dataStore.edit { it[PreferencesKeys.HIDE_COMPLETED] = hideCompleted }
    }

    private object PreferencesKeys {
        val SORT_ORDER = preferencesKey<String>("sort_order")
        val HIDE_COMPLETED = preferencesKey<Boolean>("hide_completed")
    }
}