package com.fady.venuevoyage.domain.usecases.auth

import com.fady.venuevoyage.domain.repository.AuthRepository
import com.fady.venuevoyage.presentation.utils.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        emit(authRepository.isLoggedIn())
    }.flowOn(Dispatchers.IO)
}
