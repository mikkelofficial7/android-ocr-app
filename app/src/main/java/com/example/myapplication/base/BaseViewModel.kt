package com.example.myapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {
    internal val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun handleFailure() {
        isLoadingLiveData.postValue( false)
    }

    protected fun safeScopeFun(error :(Throwable) -> Unit) : CoroutineScope {
      return viewModelScope + CoroutineExceptionHandler { coroutineContext, throwable ->
            error.invoke(throwable)
        }
    }
}
