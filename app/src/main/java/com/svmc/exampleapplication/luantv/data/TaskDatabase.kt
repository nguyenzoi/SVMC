package com.svmc.exampleapplication.luantv.data

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.svmc.exampleapplication.luantv.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

private const val TAG = "TaskDatabase"

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d(TAG, " onCreate database ")
            val dao = database.get().taskDao()
//            need determine different launch and aync
            val job = applicationScope.launch {
                dao.insert(Task("Brushing teeth", important = true))
                dao.insert(Task("cooking"))
                dao.insert(Task("washing", completed = true))
            }

            Log.d(
                TAG, "isActive ${job.isActive} + isCancel + ${job.isCancelled} " +
                        "Completed ${job.isCompleted}"
            )

        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            Log.d(TAG, " open database ")
        }

    }
}