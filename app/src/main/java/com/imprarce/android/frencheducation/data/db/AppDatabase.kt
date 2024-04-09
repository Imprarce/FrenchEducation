package com.imprarce.android.frencheducation.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryDao
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryDbEntity
import com.imprarce.android.frencheducation.data.db.progress.room.*
import com.imprarce.android.frencheducation.data.db.task.room.TaskDao
import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity
import com.imprarce.android.frencheducation.data.db.task_completed.room.TaskCompletedDao
import com.imprarce.android.frencheducation.data.db.task_completed.room.TaskCompletedDbEntity
import com.imprarce.android.frencheducation.data.db.user.room.UserDao
import com.imprarce.android.frencheducation.data.db.user.room.UserDbEntity

@Database(entities = [UserDbEntity::class, ProgressDbEntity::class, ModuleDbEntity::class, TaskDbEntity::class, ModuleTasksDbEntity::class, DictionaryDbEntity::class,
                     TaskCompletedDbEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun progressDao(): ProgressDao
    abstract fun taskDao(): TaskDao
    abstract fun moduleTasksDao(): ModuleTasksDao
    abstract fun moduleDao(): ModuleDao
    abstract fun dictionaryDao(): DictionaryDao
    abstract fun taskCompletedDao(): TaskCompletedDao


}
