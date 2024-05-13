package com.imprarce.android.frencheducation.di

import android.content.Context
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.repository.comment.CommentNetworkRepository
import com.imprarce.android.network.repository.comment.CommentNetworkRepositoryImpl
import com.imprarce.android.network.repository.community.CommunityNetworkRepository
import com.imprarce.android.network.repository.community.CommunityNetworkRepositoryImpl
import com.imprarce.android.network.repository.user.UserRepositoryNetwork
import com.imprarce.android.network.repository.user.UserRepositoryNetworkImpl
import com.imprarce.android.network.repository.dictionary.DictionaryNetworkRepository
import com.imprarce.android.network.repository.dictionary.DictionaryNetworkRepositoryImpl
import com.imprarce.android.network.repository.module.ModuleNetworkRepository
import com.imprarce.android.network.repository.module.ModuleNetworkRepositoryImpl
import com.imprarce.android.network.repository.module_progress.ModuleProgressNetworkRepository
import com.imprarce.android.network.repository.module_progress.ModuleProgressNetworkRepositoryImpl
import com.imprarce.android.network.repository.module_tasks.ModuleTasksNetworkRepository
import com.imprarce.android.network.repository.module_tasks.ModuleTasksNetworkRepositoryImpl
import com.imprarce.android.network.repository.task.TaskNetworkRepository
import com.imprarce.android.network.repository.task.TaskNetworkRepositoryImpl
import com.imprarce.android.network.repository.task_completed.TaskCompletedNetworkRepository
import com.imprarce.android.network.repository.task_completed.TaskCompletedNetworkRepositoryImpl
import com.imprarce.android.network.repository.video.VideoNetworkRepository
import com.imprarce.android.network.repository.video.VideoNetworkRepositoryImpl
import com.imprarce.android.network.utils.Constants.BASE_URL
import com.imprarce.android.network.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context: Context
    ) = SessionManager(context)

    @Provides
    fun provideNetworkApi(): NetworkApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkApi::class.java)

    }

    @Provides
    fun provideUserRepositoryNetwork(
        networkApi: NetworkApi,
        sessionManager: SessionManager
    ): UserRepositoryNetwork {
        return UserRepositoryNetworkImpl(
            networkApi, sessionManager
        )
    }

    @Provides
    fun provideDictionaryNetworkRepository(
        networkApi: NetworkApi
    ): DictionaryNetworkRepository {
        return DictionaryNetworkRepositoryImpl(networkApi)
    }

    @Provides
    fun provideModuleTasksNetworkRepository(
        networkApi: NetworkApi
    ): ModuleTasksNetworkRepository {
        return ModuleTasksNetworkRepositoryImpl(networkApi)
    }

    @Provides
    fun provideModuleNetworkRepository(
        networkApi: NetworkApi
    ): ModuleNetworkRepository {
        return ModuleNetworkRepositoryImpl(networkApi)
    }

    @Provides
    fun provideTaskNetworkRepository(
        networkApi: NetworkApi
    ): TaskNetworkRepository {
        return TaskNetworkRepositoryImpl(networkApi)
    }

    @Provides
    fun provideTaskCompletedNetworkRepository(
        networkApi: NetworkApi,
        sessionManager: SessionManager
    ): TaskCompletedNetworkRepository {
        return TaskCompletedNetworkRepositoryImpl(networkApi, sessionManager)
    }

    @Provides
    fun provideModuleProgressNetworkRepository(
        networkApi: NetworkApi,
        sessionManager: SessionManager
    ): ModuleProgressNetworkRepository {
        return ModuleProgressNetworkRepositoryImpl(networkApi, sessionManager)
    }

    @Provides
    fun provideCommentNetworkRepository(
        networkApi: NetworkApi,
        sessionManager: SessionManager
    ): CommentNetworkRepository {
        return CommentNetworkRepositoryImpl(networkApi, sessionManager)
    }

    @Provides
    fun provideCommunityNetworkRepository(
        networkApi: NetworkApi,
        sessionManager: SessionManager
    ): CommunityNetworkRepository {
        return CommunityNetworkRepositoryImpl(networkApi, sessionManager)
    }

    @Provides
    fun provideVideoNetworkRepository(
        networkApi: NetworkApi,
        sessionManager: SessionManager
    ): VideoNetworkRepository {
        return VideoNetworkRepositoryImpl(networkApi, sessionManager)
    }
}