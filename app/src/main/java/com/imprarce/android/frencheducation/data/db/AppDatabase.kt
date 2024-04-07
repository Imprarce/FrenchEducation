package com.imprarce.android.frencheducation.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imprarce.android.frencheducation.data.db.progress.room.*
import com.imprarce.android.frencheducation.data.db.task.room.TaskDao
import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity
import com.imprarce.android.frencheducation.data.db.user.room.UserDao
import com.imprarce.android.frencheducation.data.db.user.room.UserDbEntity

@Database(entities = [UserDbEntity::class, ProgressDbEntity::class, ModuleDbEntity::class, TaskDbEntity::class, ModuleTasksDbEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun progressDao(): ProgressDao
    abstract fun taskDao(): TaskDao
    abstract fun moduleTasksDao(): ModuleTasksDao
    abstract fun moduleDao(): ModuleDao

}
