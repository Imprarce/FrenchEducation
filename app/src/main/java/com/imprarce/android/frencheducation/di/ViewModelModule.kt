package com.imprarce.android.frencheducation.di

import com.imprarce.android.frencheducation.data.api.repository.FirebaseRepository
import com.imprarce.android.frencheducation.data.db.AppDatabase
import com.imprarce.android.frencheducation.data.db.user.room.UserRepository
import com.imprarce.android.frencheducation.ui.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModel(
        repository: FirebaseRepository,
        userRepository: UserRepository,
        roomDatabase: AppDatabase
    ): MainViewModel {
        return MainViewModel(repository, userRepository, roomDatabase)
    }

}