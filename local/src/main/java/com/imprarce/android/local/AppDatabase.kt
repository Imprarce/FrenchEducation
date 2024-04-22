package com.imprarce.android.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.imprarce.android.frencheducation.data.db.module_progress.room.*
import com.imprarce.android.local.dictionary.room.DictionaryDao
import com.imprarce.android.local.dictionary.room.DictionaryDbEntity
import com.imprarce.android.local.module_progress.room.ModuleProgressDao
import com.imprarce.android.local.module_progress.room.ModuleProgressDbEntity
import com.imprarce.android.local.task.room.TaskDao
import com.imprarce.android.local.task.room.TaskDbEntity
import com.imprarce.android.local.task_completed.room.TaskCompletedDao
import com.imprarce.android.local.task_completed.room.TaskCompletedDbEntity
import com.imprarce.android.local.user.room.UserDao
import com.imprarce.android.local.user.room.UserDbEntity

@Database(
    entities = [UserDbEntity::class, ModuleProgressDbEntity::class, ModuleDbEntity::class, TaskDbEntity::class, ModuleTasksDbEntity::class, DictionaryDbEntity::class,
        TaskCompletedDbEntity::class], version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun moduleProgressDao(): ModuleProgressDao
    abstract fun taskDao(): TaskDao
    abstract fun moduleTasksDao(): ModuleTasksDao
    abstract fun moduleDao(): ModuleDao
    abstract fun dictionaryDao(): DictionaryDao
    abstract fun taskCompletedDao(): TaskCompletedDao

    companion object {

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "frenchDb.db"
            )
                .addMigrations(MIGRATION_3_4)
                .build()
        }
    }

}
