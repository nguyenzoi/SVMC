package com.svmc.exampleapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.svmc.exampleapplication.dj.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    class CallBack @Inject constructor(
        private val database: Provider<TaskDataBase>,
        @ApplicationScope private val application: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val taskDao = database.get().taskDao()
            application.launch {
                taskDao.insert(Task("call mom", importance = true))
                taskDao.insert(Task("buy rice", completed = true))
                taskDao.insert(Task("brush teeth", importance = true))
                taskDao.insert(Task("walking", importance = true))
                taskDao.insert(Task("watching tivi"))
                taskDao.insert(Task("wash hand"))
            }
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
        }
    }
}