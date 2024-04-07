package com.imprarce.android.frencheducation.di


import android.content.Context
import androidx.room.Room
import com.imprarce.android.frencheducation.data.db.AppDatabase
import com.imprarce.android.frencheducation.data.db.module.ModuleRepositoryImpl
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleDao
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleRepository
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
            "app_database"
        ).build()
    }


    @Provides
    fun provideModuleRepository(moduleDao: ModuleDao): ModuleRepository {
        return ModuleRepositoryImpl(moduleDao)
    }

    @Provides
    fun provideModuleDao(database: AppDatabase): ModuleDao {
        return database.moduleDao()
    }
}