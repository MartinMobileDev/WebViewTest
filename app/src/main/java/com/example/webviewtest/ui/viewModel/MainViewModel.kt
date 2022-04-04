package com.example.webviewtest.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webviewtest.domain.GetNewsUseCase
import com.example.webviewtest.domain.model.Notice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) :
    ViewModel() {

    val noticeListModel = MutableLiveData<List<Notice>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreateApi() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getNewsUseCase.getAllNewsFromApi()
            if(!result.isNullOrEmpty()){
                noticeListModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun onCreateDatabase() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getNewsUseCase.getAllNewsFromDatabase()
            if(!result.isNullOrEmpty()){
                noticeListModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun clearNews() {
        noticeListModel.postValue(emptyList())
    }

}