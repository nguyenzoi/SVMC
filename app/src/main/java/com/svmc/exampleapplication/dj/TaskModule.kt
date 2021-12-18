package com.svmc.exampleapplication.dj

import android.app.Application
import androidx.room.Room
import com.svmc.exampleapplication.data.TaskDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class TaskModule {

    @Provides
    @Singleton
    fun provideTaskDao(
        app: Application,
        callBack: TaskDataBase.CallBack
    ) =
        Room.databaseBuilder(app, TaskDataBase::class.java, "task_table")
            .fallbackToDestructiveMigration()
            .addCallback(callBack)
            .build()

    @Provides
    @Singleton
    fun provideTaskDao(db: TaskDataBase) = db.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope