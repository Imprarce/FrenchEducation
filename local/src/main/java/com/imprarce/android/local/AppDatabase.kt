package com.imprarce.android.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.imprarce.android.frencheducation.data.db.module_progress.room.*
import com.imprarce.android.local.comment.room.CommentDao
import com.imprarce.android.local.comment.room.CommentDbEntity
import com.imprarce.android.local.community.room.CommunityDao
import com.imprarce.android.local.community.room.CommunityDbEntity
import com.imprarce.android.local.dictionary.room.DictionaryDao
import com.imprarce.android.local.dictionary.room.DictionaryDbEntity
import com.imprarce.android.local.favorite_word.room.FavoriteWordDao
import com.imprarce.android.local.favorite_word.room.FavoriteWordDbEntity
import com.imprarce.android.local.ielts.room.IeltsDao
import com.imprarce.android.local.ielts.room.IeltsDbEntity
import com.imprarce.android.local.ielts_progress.room.IeltsProgressDao
import com.imprarce.android.local.ielts_progress.room.IeltsProgressDbEntity
import com.imprarce.android.local.ielts_with_tasks.room.IeltsWithTasksDao
import com.imprarce.android.local.ielts_with_tasks.room.IeltsWithTasksDbEntity
import com.imprarce.android.local.module_progress.room.ModuleProgressDao
import com.imprarce.android.local.module_progress.room.ModuleProgressDbEntity
import com.imprarce.android.local.task.room.TaskDao
import com.imprarce.android.local.task.room.TaskDbEntity
import com.imprarce.android.local.task_completed.room.TaskCompletedDao
import com.imprarce.android.local.task_completed.room.TaskCompletedDbEntity
import com.imprarce.android.local.task_specially_ielts.room.TaskSpeciallyIeltsDao
import com.imprarce.android.local.task_specially_ielts.room.TaskSpeciallyIeltsDbEntity
import com.imprarce.android.local.user.room.UserDao
import com.imprarce.android.local.user.room.UserDbEntity
import com.imprarce.android.local.video.room.VideoDao
import com.imprarce.android.local.video.room.VideoDbEntity

@Database(
    entities = [UserDbEntity::class, ModuleProgressDbEntity::class, ModuleDbEntity::class, TaskDbEntity::class, ModuleTasksDbEntity::class, DictionaryDbEntity::class,
        TaskCompletedDbEntity::class, CommentDbEntity::class, CommunityDbEntity::class, FavoriteWordDbEntity::class, IeltsDbEntity::class, IeltsProgressDbEntity::class,
               IeltsWithTasksDbEntity::class, TaskSpeciallyIeltsDbEntity::class, VideoDbEntity::class], version = 7
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun moduleProgressDao(): ModuleProgressDao
    abstract fun taskDao(): TaskDao
    abstract fun moduleTasksDao(): ModuleTasksDao
    abstract fun moduleDao(): ModuleDao
    abstract fun dictionaryDao(): DictionaryDao
    abstract fun taskCompletedDao(): TaskCompletedDao
    abstract fun commentDao(): CommentDao
    abstract fun communityDao(): CommunityDao
    abstract fun favoriteWordDao(): FavoriteWordDao
    abstract fun ieltsDao(): IeltsDao
    abstract fun ieltsProgressDao(): IeltsProgressDao
    abstract fun ieltsWithTasksDao(): IeltsWithTasksDao
    abstract fun taskSpeciallyIeltsDao(): TaskSpeciallyIeltsDao
    abstract fun videoDao(): VideoDao


    companion object {

        private val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "frenchDb.db"
            )
                .addMigrations(MIGRATION_6_7)
                .build()
        }
    }

}
