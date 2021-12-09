package com.svmc.exampleapplication.luantv.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.svmc.exampleapplication.luantv.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataBase(
        app: Application,
        callback: TaskDatabase.Callback
    ) {
        Room.databaseBuilder(app, TaskDatabase::class.java, "task_table")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build();
    }

    @Provides
    @Singleton
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope