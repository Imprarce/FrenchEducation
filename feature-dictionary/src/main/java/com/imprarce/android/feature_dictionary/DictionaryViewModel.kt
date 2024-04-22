package com.imprarce.android.feature_dictionary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.dictionary.DictionaryListItem
import com.imprarce.android.local.dictionary.room.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    private var _dictionaryList = MutableLiveData<List<DictionaryListItem>>()
    val dictionaryList: LiveData<List<DictionaryListItem>> = _dictionaryList

    init {
        loadDictionary()
    }

    private fun loadDictionary(){
        viewModelScope.launch {
            when (val response = dictionaryRepository.getAllWords()) {
                is ResponseRoom.Success -> {
                    val dictionary = response.result
                    _dictionaryList.value = dictionary.map { DictionaryListItem(it) }
                }
                is ResponseRoom.Failure -> {
                    Log.e("DictionaryViewModel", "Failed to load dictionary: ${response.exception}")
                }
                is ResponseRoom.Loading -> {
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DictionaryViewModel", "DictionaryViewModel cleared")
    }
}