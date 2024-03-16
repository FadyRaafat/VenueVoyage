package com.fady.venuevoyage.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.fady.venuevoyage.domain.usecases.profile.DeleteUserUseCase
import com.fady.venuevoyage.presentation.utils.base.BaseViewModel
import com.fady.venuevoyage.presentation.utils.common.Resource
import com.fady.venuevoyage.presentation.utils.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val deleteUserUseCase: DeleteUserUseCase,
) : BaseViewModel() {
    var openCloseDrawer = SingleLiveEvent<Void>()

    fun openCloseDrawer() =
        openCloseDrawer.call()


    private val _deleteUserSuccess = MutableSharedFlow<Boolean>()
    val deleteUserSuccess = _deleteUserSuccess

    fun deleteUser() {
        deleteUserUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _deleteUserSuccess.emit(true)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}