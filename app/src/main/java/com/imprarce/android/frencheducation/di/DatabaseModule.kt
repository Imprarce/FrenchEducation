package com.imprarce.android.frencheducation.di


import android.app.Application
import android.content.Context
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleDao
import com.imprarce.android.frencheducation.data.db.module_progress.room.ModuleTasksDao
import com.imprarce.android.local.AppDatabase
import com.imprarce.android.local.comment.CommentRepositoryImpl
import com.imprarce.android.local.comment.room.CommentDao
import com.imprarce.android.local.comment.room.CommentRepository
import com.imprarce.android.local.community.CommunityRepositoryImpl
import com.imprarce.android.local.community.room.CommunityDao
import com.imprarce.android.local.community.room.CommunityRepository
import com.imprarce.android.local.dictionary.DictionaryRepositoryImpl
import com.imprarce.android.local.dictionary.room.DictionaryDao
import com.imprarce.android.local.dictionary.room.DictionaryRepository
import com.imprarce.android.local.module.ModuleRepositoryImpl
import com.imprarce.android.local.module.room.ModuleRepository
import com.imprarce.android.local.module_progress.ProgressRepositoryImpl
import com.imprarce.android.local.module_progress.room.ModuleProgressDao
import com.imprarce.android.local.module_progress.room.ModuleProgressRepository
import com.imprarce.android.local.module_tasks.ModuleTasksRepositoryImpl
import com.imprarce.android.local.module_tasks.room.ModuleTasksRepository
import com.imprarce.android.local.task.TaskRepositoryImpl
import com.imprarce.android.local.task.room.TaskDao
import com.imprarce.android.local.task.room.TaskRepository
import com.imprarce.android.local.task_completed.TaskCompletedRepositoryImpl
import com.imprarce.android.local.task_completed.room.TaskCompletedDao
import com.imprarce.android.local.task_completed.room.TaskCompletedRepository
import com.imprarce.android.local.user.UserRepositoryImpl
import com.imprarce.android.local.user.room.UserDao
import com.imprarce.android.local.user.room.UserRepository
import com.imprarce.android.local.video.VideoRepositoryImpl
import com.imprarce.android.local.video.room.VideoDao
import com.imprarce.android.local.video.room.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun getAppDataBase(app: Application): AppDatabase{
        return AppDatabase.getInstance(app.applicationContext)
    }

    @Provides
    fun provideModuleRepository(moduleDao: ModuleDao): ModuleRepository {
        return ModuleRepositoryImpl(moduleDao)
    }

    @Provides
    fun provideModuleDao(database: AppDatabase): ModuleDao {
        return database.moduleDao()
    }

    @Provides
    fun provideModuleTasksRepository(moduleTasksDao: ModuleTasksDao): ModuleTasksRepository {
        return ModuleTasksRepositoryImpl(moduleTasksDao)
    }

    @Provides
    fun provideModuleTasksDao(database: AppDatabase): ModuleTasksDao {
        return database.moduleTasksDao()
    }

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideDictionaryRepository(dictionaryDao: DictionaryDao): DictionaryRepository {
        return DictionaryRepositoryImpl(dictionaryDao)
    }

    @Provides
    fun provideDictionaryDao(database: AppDatabase): DictionaryDao {
        return database.dictionaryDao()
    }

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideTaskCompletedRepository(taskCompletedDao: TaskCompletedDao): TaskCompletedRepository {
        return TaskCompletedRepositoryImpl(taskCompletedDao)
    }

    @Provides
    fun provideTaskCompletedDao(database: AppDatabase): TaskCompletedDao {
        return database.taskCompletedDao()
    }

    @Provides
    fun provideModuleProgressRepository(moduleProgressDao: ModuleProgressDao): ModuleProgressRepository {
        return ProgressRepositoryImpl(moduleProgressDao)
    }

    @Provides
    fun provideModuleProgressDao(database: AppDatabase): ModuleProgressDao {
        return database.moduleProgressDao()
    }

    @Provides
    fun provideCommunityRepository(communityDao: CommunityDao): CommunityRepository {
        return CommunityRepositoryImpl(communityDao)
    }

    @Provides
    fun provideCommunityDao(database: AppDatabase): CommunityDao {
        return database.communityDao()
    }

    @Provides
    fun provideCommentRepository(commentDao: CommentDao): CommentRepository {
        return CommentRepositoryImpl(commentDao)
    }

    @Provides
    fun provideCommentDao(database: AppDatabase): CommentDao {
        return database.commentDao()
    }

    @Provides
    fun provideVideoRepository(videoDao: VideoDao): VideoRepository {
        return VideoRepositoryImpl(videoDao)
    }

    @Provides
    fun provideVideoDao(database: AppDatabase): VideoDao {
        return database.videoDao()
    }
}