package com.fady.venuevoyage.presentation.ui.profile

import androidx.lifecycle.viewModelScope
import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.domain.usecases.profile.GetUserProfileUseCase
import com.fady.venuevoyage.presentation.utils.base.BaseViewModel
import com.fady.venuevoyage.presentation.utils.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getUserProfileUseCase: GetUserProfileUseCase,
) : BaseViewModel() {

    // Get User Profile
    private val _getUserProfileSuccess = MutableSharedFlow<UserProfile>()
    val getUserProfileSuccess = _getUserProfileSuccess

    fun getUserProfile() {
        getUserProfileUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _getUserProfileSuccess.emit(result.value)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

}