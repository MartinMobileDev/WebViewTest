package com.example.webviewtest.ui.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webviewtest.R
import com.example.webviewtest.WebViewTest
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
            if (!result.isNullOrEmpty()) {
                noticeListModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun updateNotice(id: Int) {
        viewModelScope.launch {
            getNewsUseCase.updateNotice(id)
        }
    }

    fun onCreateDatabase() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getNewsUseCase.getAllNewsFromDatabase()
            if (!result.isNullOrEmpty()) {
                noticeListModel.postValue(result)
                isLoading.postValue(false)
            } else {
                Toast.makeText(
                    WebViewTest.applicationContext(),
                    WebViewTest.applicationContext().getString(R.string.room_empty),
                    Toast.LENGTH_SHORT
                )
                    .show()
                isLoading.postValue(false)
            }
        }
    }

    fun clearNews() {
        noticeListModel.postValue(emptyList())
    }

}