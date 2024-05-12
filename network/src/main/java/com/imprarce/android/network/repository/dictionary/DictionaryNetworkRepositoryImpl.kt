package com.imprarce.android.network.repository.dictionary

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.api.NetworkApi
import com.imprarce.android.network.model.dictionary.DictionaryWord
import javax.inject.Inject

class DictionaryNetworkRepositoryImpl @Inject constructor(
    private val dictionaryApi: NetworkApi
) : DictionaryNetworkRepository {

    override suspend fun getDictionary(): ResponseNetwork<List<DictionaryWord>> {
        return try {
            val response = dictionaryApi.getDictionary()
            if (response.isSuccessful) {
                val words = response.body() ?: emptyList()
                ResponseNetwork.Success(words)
            } else {
                ResponseNetwork.Failure(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseNetwork.Failure(e.message ?: "Возникла какая-то ошибка")
        }
    }
}