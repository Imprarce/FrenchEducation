package com.imprarce.android.feature_dictionary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.feature_dictionary.helpers.Converter
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.dictionary.DictionaryListItem
import com.imprarce.android.local.dictionary.room.DictionaryRepository
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.dictionary.DictionaryWord
import com.imprarce.android.network.repository.dictionary.DictionaryNetworkRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryRepositoryImpl: DictionaryNetworkRepositoryImpl,
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    private var _dictionaryListFromRoom = MutableLiveData<List<DictionaryListItem>>()
    val dictionaryListFromRoom: LiveData<List<DictionaryListItem>> = _dictionaryListFromRoom

    private var _dictionaryListFromNetwork = MutableLiveData<List<DictionaryWord>?>()
    val dictionaryListFromNetwork: LiveData<List<DictionaryWord>?> = _dictionaryListFromNetwork

    init {
        loadDictionaryFromNetwork()
    }

    // TODO: ДОДЕЛАТЬ ЛОКАЛЬНОЕ СОХРАНЕНИЕ И ЗАГРУЗКУ
    private fun loadDictionary(){
        viewModelScope.launch {
            when (val response = dictionaryRepository.getAllWords()) {
                is ResponseRoom.Success -> {
                    val dictionaryWords = response.result.map { Converter.toDictionaryListItem(it) }
                    _dictionaryListFromRoom.value = dictionaryWords
                }
                is ResponseRoom.Failure -> {
                    Log.e("DictionaryViewModel", "Failed to load dictionary: ${response.exception}")
                }
                is ResponseRoom.Loading -> {
                }
            }
        }
    }

    private fun loadDictionaryFromNetwork(){
        viewModelScope.launch {
            when (val response = dictionaryRepositoryImpl.getDictionary()){
                is ResponseNetwork.Success -> {
                    _dictionaryListFromNetwork.value = response.result
                    _dictionaryListFromRoom.value = Converter.toDictionaryListItems(
                        _dictionaryListFromNetwork.value!!
                    )
                }
                else -> {}
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DictionaryViewModel", "DictionaryViewModel cleared")
    }
}