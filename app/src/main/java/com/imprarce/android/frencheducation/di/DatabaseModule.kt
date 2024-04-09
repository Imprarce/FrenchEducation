package com.imprarce.android.frencheducation.di


import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.imprarce.android.frencheducation.data.db.AppDatabase
import com.imprarce.android.frencheducation.data.db.dictionary.DictionaryRepositoryImpl
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryDao
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryRepository
import com.imprarce.android.frencheducation.data.db.module.ModuleRepositoryImpl
import com.imprarce.android.frencheducation.data.db.module_tasks.ModuleTasksRepositoryImpl
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleDao
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleRepository
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksDao
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksRepository
import com.imprarce.android.frencheducation.data.db.task.TaskRepositoryImpl
import com.imprarce.android.frencheducation.data.db.task.room.TaskDao
import com.imprarce.android.frencheducation.data.db.task.room.TaskRepository
import com.imprarce.android.frencheducation.data.db.task_completed.TaskCompletedRepositoryImpl
import com.imprarce.android.frencheducation.data.db.task_completed.room.TaskCompletedDao
import com.imprarce.android.frencheducation.data.db.task_completed.room.TaskCompletedRepository
import com.imprarce.android.frencheducation.data.db.user.UserRepositoryImpl
import com.imprarce.android.frencheducation.data.db.user.room.UserDao
import com.imprarce.android.frencheducation.data.db.user.room.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "frenchDb.db"
        ).addMigrations(MIGRATION_1_2)
            .build()
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
    fun provideTaskCompletedRepository(taskCompletedDao: TaskCompletedDao) : TaskCompletedRepository{
        return TaskCompletedRepositoryImpl(taskCompletedDao)
    }

    @Provides
    fun provideTaskCompletedDao(database: AppDatabase): TaskCompletedDao {
        return database.taskCompletedDao()
    }

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
    }

}