package com.fady.venuevoyage.data.repositories

import com.fady.venuevoyage.data.datasource.local.LocalDataSource
import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.domain.repository.ProfileRepository
import com.fady.venuevoyage.presentation.utils.common.Resource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
) : ProfileRepository {
    override fun getUserProfile(): Resource<UserProfile> {
        return Resource.Success(LocalDataSource.userProfile ?: UserProfile())
    }

    override fun deleteUserProfile(): Resource<Unit> {
        LocalDataSource.clearToken()
        return Resource.Success(Unit)
    }

}