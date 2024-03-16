package com.fady.venuevoyage.domain.usecases.profile

import com.fady.venuevoyage.domain.repository.ProfileRepository
import com.fady.venuevoyage.presentation.utils.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        emit(profileRepository.deleteUserProfile())
    }.flowOn(Dispatchers.IO)
}
