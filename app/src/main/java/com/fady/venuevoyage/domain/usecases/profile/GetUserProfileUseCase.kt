package com.fady.venuevoyage.domain.usecases.profile

import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.domain.repository.ProfileRepository
import com.fady.venuevoyage.presentation.utils.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(): Flow<Resource<UserProfile>> = flow {
        emit(Resource.Loading)
        emit(profileRepository.getUserProfile())
    }.flowOn(Dispatchers.IO)
}
