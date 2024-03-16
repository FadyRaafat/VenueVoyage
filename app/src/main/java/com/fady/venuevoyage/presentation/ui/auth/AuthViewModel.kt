package com.fady.venuevoyage.presentation.ui.auth

import androidx.lifecycle.viewModelScope
import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.domain.usecases.auth.IsLoggedInUseCase
import com.fady.venuevoyage.domain.usecases.auth.LoginUseCase
import com.fady.venuevoyage.domain.usecases.auth.SaveUserUseCase
import com.fady.venuevoyage.presentation.utils.base.BaseViewModel
import com.fady.venuevoyage.presentation.utils.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val saveUserUseCase: SaveUserUseCase,
    val loginUseCase: LoginUseCase,
    val isLoggedInUseCase: IsLoggedInUseCase
) : BaseViewModel() {

    // Save User
    private val _saveUserSuccess = MutableSharedFlow<Boolean>()
    val saveUserSuccess = _saveUserSuccess

    fun register(userProfile: UserProfile) {
        saveUserUseCase(userProfile).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading.value = true
                }

                is Resource.Success -> {
                    showLoading.value = false
                    _saveUserSuccess.emit(true)
                }

                is Resource.Failure -> {
                    _saveUserSuccess.emit(false)
                    showLoading.value = false
                    showApiError.value = result
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    // Login
    private val _loginSuccess = MutableSharedFlow<Boolean>()
    val loginSuccess = _loginSuccess

    fun login(email: String, password: String) {
        loginUseCase(email, password).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading.value = true
                }

                is Resource.Success -> {
                    showLoading.value = false
                    _loginSuccess.emit(true)
                }

                is Resource.Failure -> {
                    _loginSuccess.emit(false)
                    showLoading.value = false
                    showApiError.value = result
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    // Is Logged In
    private val _isLoggedIn = MutableSharedFlow<Boolean>()
    val isLoggedIn = _isLoggedIn

    fun isLoggedIn() {
        isLoggedInUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _isLoggedIn.emit(result.value)
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

}