package com.imprarce.android.network.repository.dictionary

import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.dictionary.DictionaryWord

interface DictionaryNetworkRepository {
    suspend fun getDictionary(): ResponseNetwork<List<DictionaryWord>>
}