package com.imprarce.android.frencheducation.ui.main.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.frencheducation.data.db.dictionary.DictionaryListItem
import com.imprarce.android.frencheducation.data.db.dictionary.room.DictionaryRepository
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

    fun loadDictionary(){
        viewModelScope.launch {
            val dictionary = dictionaryRepository.getAllWords()
            _dictionaryList.value = dictionary.map { DictionaryListItem(it) }
        }
    }
}