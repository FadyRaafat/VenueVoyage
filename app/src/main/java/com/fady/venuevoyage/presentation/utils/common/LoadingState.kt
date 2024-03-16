package com.fady.venuevoyage.presentation.utils.common

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.fady.venuevoyage.BR

class LoadingState: BaseObservable() {

    fun loadingState(){
        showRetry = false
        showError = false
        errorMessage = ""
        showLoading = true

    }

    fun loadedState(){
        showLoading = false
        showRetry = false
        showError = false
        errorMessage = ""
    }

    fun errorState(){
        showLoading = false
        showRetry = true
        showError = false
        errorMessage = ""
    }

    fun errorState(error: String){
        showLoading = false
        showRetry = true
        showError = true
        errorMessage = error
    }

    var retry: Retry? = null

    fun setRetry(onRetry: ()-> Unit){
        retry = object : Retry {
            override fun retry() {
                onRetry.invoke()
            }
        }
    }

    @get:Bindable
    var showLoading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showLoading)
        }

    @get:Bindable
    var showRetry: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showRetry)
        }

    @get:Bindable
    var showError: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showError)
        }

    @get:Bindable
    var errorMessage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMessage)
        }

}