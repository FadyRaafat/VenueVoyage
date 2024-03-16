package com.fady.venuevoyage.domain.usecases.auth

import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.domain.repository.AuthRepository
import com.fady.venuevoyage.presentation.utils.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(userProfile: UserProfile): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading)
            emit(authRepository.saveUser(userProfile))
        }.flowOn(Dispatchers.IO)
}
