package com.fady.venuevoyage.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.fady.venuevoyage.data.models.VenuesResponse
import com.fady.venuevoyage.domain.usecases.home.GetVenuesUseCase
import com.fady.venuevoyage.presentation.utils.base.BaseViewModel
import com.fady.venuevoyage.presentation.utils.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    val getVenuesUseCase: GetVenuesUseCase,
) : BaseViewModel() {

    // getVenues
    private val _getVenuesSuccess = MutableSharedFlow<Boolean>()
    val getVenuesSuccess = _getVenuesSuccess
    private var venuesResponse: VenuesResponse? = null

    fun getVenues(location: Array<Double>) {
        getVenuesUseCase(location).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading.value = true
                }

                is Resource.Success -> {
                    venuesResponse = result.value
                    showLoading.value = false
                    _getVenuesSuccess.emit(true)
                }

                is Resource.Failure -> {
                    _getVenuesSuccess.emit(false)
                    showLoading.value = false
                    showApiError.value = result
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getVenuesResponse(): VenuesResponse? = venuesResponse

}