package com.svmc.exampleapplication.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = "task_table")
@Parcelize
data class Task(
    val name: String,
    val importance: Boolean = false,
    val completed: Boolean = false,
    val createdDate: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0): Parcelable {

    val created: String
        get() = DateFormat.getTimeInstance().format(createdDate)
}
