package com.imprarce.android.network.api

import android.net.Uri
import com.imprarce.android.network.SimpleResponse
import com.imprarce.android.network.model.comment.Comment
import com.imprarce.android.network.model.community.CommunityResponse
import com.imprarce.android.network.model.community.CommunitySend
import com.imprarce.android.network.model.user.PhotoUploadResponse
import com.imprarce.android.network.model.user.User
import com.imprarce.android.network.model.user.UserLoginAndReg
import com.imprarce.android.network.model.dictionary.DictionaryWord
import com.imprarce.android.network.model.module.Module
import com.imprarce.android.network.model.module_progress.ModuleProgress
import com.imprarce.android.network.model.module_tasks.ModuleTasks
import com.imprarce.android.network.model.task.Task
import com.imprarce.android.network.model.task_completed.TaskCompleted
import com.imprarce.android.network.model.video.Video
import com.imprarce.android.network.utils.Constants.API_VERSION
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface NetworkApi {

    // USER

    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/users/register")
    suspend fun createAccount(
        @Body user: UserLoginAndReg
    ): SimpleResponse

    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/users/login")
    suspend fun login(
        @Body user: UserLoginAndReg
    ): SimpleResponse

    @GET("$API_VERSION/users/get")
    suspend fun getUserByEmail(
        @Query("email") email: String
    ): User

    @Multipart
    @POST("$API_VERSION/users/images")
    suspend fun uploadPhoto(
        @Query("email") email: String,
        @Part photo: MultipartBody.Part
    ): Response<PhotoUploadResponse>

    @POST("$API_VERSION/users/updateName")
    suspend fun changeName(
        @Query("email") email: String,
        @Query("name") name: String
    ): SimpleResponse


    @GET("$API_VERSION/uploaded_images/get")
    suspend fun getPhotoUrl(
        @Query("email") email: String
    ): Response<Uri>

    @GET("$API_VERSION/users/name")
    suspend fun getName(
        @Query("email") email: String
    ): Response<String>


    // DICTIONARY

    @GET("${API_VERSION}/dictionary/get")
    suspend fun getDictionary(): Response<List<DictionaryWord>>

    // MODULE

    @GET("${API_VERSION}/module/get")
    suspend fun getModules(): Response<List<Module>>

    // MODULE_PROGRESS

    @GET("${API_VERSION}/module_progress/get")
    suspend fun getModulesProgress(
        @Query("id_user") userId: Int,
        @Query("id_module") moduleId: Int
    ): Response<ModuleProgress>

    @POST("${API_VERSION}/module_progress/update")
    suspend fun setModuleProgress(
        @Body moduleProgress: ModuleProgress
    ): SimpleResponse

    @POST("${API_VERSION}/module_progress/create")
    suspend fun createModuleProgress(
        @Body moduleProgress: ModuleProgress
    ): SimpleResponse

    // TASK

    @GET("${API_VERSION}/tasks/get")
    suspend fun getTask(
        @Query("listTasks") listTasks: List<Int>
    ): Response<List<Task>>

    @GET("${API_VERSION}/task/get")
    suspend fun getTaskById(
        @Query("id_task") taskId: Int
    ): Response<Task>

    // TASK_COMPLETED

    @GET("${API_VERSION}/task_completed/get")
    suspend fun getTaskCompleted(
        @Query("id_user") userId: Int,
        @Query("id_task") taskId: Int
    ): Response<TaskCompleted>

    @GET("${API_VERSION}/tasks_completed_by_user/get")
    suspend fun getCompletedTasksForUser(
        @Query("id_user") userId: Int,
    ): Response<List<TaskCompleted>>

    @POST("${API_VERSION}/task_completed/create")
    suspend fun createTaskCompleted(
        @Body taskCompleted: TaskCompleted
    ): SimpleResponse

    // MODULE WITH TASKS

    @GET("${API_VERSION}/module_tasks/get")
    suspend fun getModuleTasks(
        @Query("id_module") moduleId: Int
    ): Response<List<ModuleTasks>>

    // COMMUNITY

    @GET("${API_VERSION}/communities/get")
    suspend fun getCommunities(): Response<List<CommunityResponse>>

    @GET("${API_VERSION}/community/get")
    suspend fun getCommunityById(
        @Query("id_community") commentId: Int
    ): Response<CommunityResponse>


    @POST("${API_VERSION}/community/create")
    suspend fun createCommunity(
        @Body communitySend: CommunitySend
    ): SimpleResponse

    @DELETE("${API_VERSION}/community/delete")
    suspend fun deleteCommunity(
        @Query("id_community") communityId: Int
    ): SimpleResponse

    // COMMENT

    @GET("${API_VERSION}/comment/get")
    suspend fun getComments(
        @Query("id_community") communityId: Int
    ): Response<List<Comment>>

    @POST("${API_VERSION}/comment/create")
    suspend fun createComment(
        @Body comment: Comment
    ): SimpleResponse

    @DELETE("${API_VERSION}/comment/delete")
    suspend fun deleteComment(
        @Query("id_comment") commentId: Int
    ): SimpleResponse

    // VIDEO

    @GET("${API_VERSION}/videos/get")
    suspend fun getVideos(): Response<List<Video>>

    @GET("${API_VERSION}/video/get")
    suspend fun getVideoById(
        @Query("id_video") videoId: Int
    ): Response<Video>

    @Multipart
    @POST("v1/video/create")
    suspend fun createVideo(
        @Part("videoId") videoId: Int,
        @Part("userId") userId: Int,
        @Part("rating") rating: Int,
        @Part("view") view: Int,
        @Part("description") description: String,
        @Part("title") title: String,
        @Part videoFile: MultipartBody.Part
    ): SimpleResponse

    @DELETE("${API_VERSION}/video/delete")
    suspend fun deleteVideo(
        @Query("id_video") videoId: Int
    ): SimpleResponse
}