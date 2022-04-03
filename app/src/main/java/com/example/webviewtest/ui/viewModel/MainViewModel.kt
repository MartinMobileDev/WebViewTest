package com.example.webviewtest.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webviewtest.data.model.Notice
import com.example.webviewtest.data.network.APIService
import com.example.webviewtest.domain.GetNewsUseCase
import com.example.webviewtest.utils.RetrofitHelper.getRetrofit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) :
    ViewModel() {

    val noticeListModel = MutableLiveData<List<Notice>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getNewsUseCase()
            if(!result.isNullOrEmpty()){
                noticeListModel.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }

    fun getAllNews() {
        isLoading.postValue(true)

        //TODO AQUI METER LO DE ROOM Y ACTULIZACION DE ITEMS DEL RECYCLERVIEW

        isLoading.postValue(false)
/*        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getNews("mobile")
            val response = call.body()
            if (call.isSuccessful) {
                val noticeList: List<Notice>? = response
                noticeListModel.postValue(noticeList!!)
            }
        }*/
    }

    fun clearNews() {
        noticeListModel.postValue(emptyList())
    }

}