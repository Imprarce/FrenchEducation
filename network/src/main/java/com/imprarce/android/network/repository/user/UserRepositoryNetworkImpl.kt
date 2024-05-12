package com.imprarce.android.network.repository.user

import android.net.Uri
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.user.User
import com.imprarce.android.network.model.user.UserLoginAndReg
import com.imprarce.android.network.utils.SessionManager
import com.imprarce.android.network.utils.getPathFromUri
import com.imprarce.android.network.utils.isNetworkConnected
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UserRepositoryNetworkImpl @Inject constructor(
    private val networkApi: NetworkApi,
    private val sessionManager: SessionManager
) : UserRepositoryNetwork {

    override suspend fun login(user: UserLoginAndReg): ResponseNetwork<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                ResponseNetwork.Failure(exception = "Нет подключения к интернету")
            }

            val result = networkApi.login(user)
            if (result.success) {
                sessionManager.updateSession(result.message, user.email)
                ResponseNetwork.Success("Авторизация успешно прошла")
            } else {
                ResponseNetwork.Failure(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e.message == "HTTP 400 ") {
                ResponseNetwork.Failure("Неправильная почта или пароль")
            } else {
                ResponseNetwork.Failure(e.message ?: "Возникла какая-то проблема")
            }
        }
    }

    override suspend fun signUp(user: UserLoginAndReg): ResponseNetwork<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                ResponseNetwork.Failure(exception = "Нет подключения к интернету")
            }

            val result = networkApi.createAccount(user)
            if (result.success) {
                sessionManager.updateSession(result.message, user.email)
                ResponseNetwork.Success("Пользователь успешно создан")
            } else {
                ResponseNetwork.Failure(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e.message == "HTTP 400 ") {
                ResponseNetwork.Failure("Данная почта уже занята")
            } else {
                ResponseNetwork.Failure(e.message ?: "Возникла какая-то проблема")
            }
        }
    }

    override suspend fun getUser(): ResponseNetwork<User> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                ResponseNetwork.Failure(exception = "Нет подключения к интернету")
            }

            val email = sessionManager.getCurrentUserEmail()
            if (email == null) {
                ResponseNetwork.Failure("Пользователь не авторизирован")
            }

            val result = networkApi.getUserByEmail(email!!)

            ResponseNetwork.Success(result)
        } catch (e: Exception) {
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то проблема")
        }
    }

    override suspend fun logOut(): ResponseNetwork<String> {
        return try {
            sessionManager.logout()
            ResponseNetwork.Success("Выход из учетной записи выполнен")
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то проблема")
        }
    }

    override suspend fun changeName(name: String) {
        try {
            val email = sessionManager.getCurrentUserEmail()

            if (email == null) {
                return
            }

            val response = networkApi.changeName(email, name)
            if (response.success) {
                println(response.message)
            } else {
                println("Change name failed")
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override suspend fun getName(): String {
        return try {
            val email = sessionManager.getCurrentUserEmail()

            if (email == null) {
                return ""
            }

            val response = networkApi.getName(email)
            if (response.isSuccessful) {
                return response.message()
            } else {
                return "Upload name failed"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    override suspend fun changePhoto(photoUri: Uri) {
        try {
            val context = sessionManager.context

            val email = sessionManager.getCurrentUserEmail()

            if (email == null) {
                return
            }

            val photoPath = getPathFromUri(context, photoUri)
            if (photoPath == null) {
                println("Failed to get file path from Uri")
                return
            }

            val photoFile = File(photoPath)
            if (!photoFile.exists()) {
                println("File does not exist")
                return
            }
            val fileName = email.replace("@", "_").replace(".", "_") + ".jpg"
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), photoFile)
            val photoBody = MultipartBody.Part.createFormData("images", fileName, requestFile)


            val response = networkApi.uploadPhoto(email, photoBody)
            if (response.isSuccessful) {
                println(response.message())
            } else {
                println("Upload photo failed")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override suspend fun getPhotoUrl(email: String): String {
        try {
            val response = networkApi.getPhotoUrl(email)
            if (response.isSuccessful) {
                val url = response.body()
                if (url != null) {
                    return url.toString()
                } else {

                }
            } else {

            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

        return ""
    }

    override suspend fun loadVideo(id_video: Int, videoUri: Uri) {
        TODO("Not yet implemented")
    }

    override suspend fun getVideoUrl(id_video: Int): String {
        TODO("Not yet implemented")
    }

}