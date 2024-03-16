package com.fady.venuevoyage.presentation.utils.base

import androidx.lifecycle.ViewModel
import com.fady.venuevoyage.presentation.utils.common.Resource
import com.fady.venuevoyage.presentation.utils.common.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    var showLoading = SingleLiveEvent<Boolean>()
    var showApiError = SingleLiveEvent<Resource.Failure>()
}