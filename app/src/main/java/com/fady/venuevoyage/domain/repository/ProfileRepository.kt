package com.fady.venuevoyage.domain.repository

import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.presentation.utils.common.Resource

interface ProfileRepository {
    fun getUserProfile() : Resource<UserProfile>

    fun deleteUserProfile() : Resource<Unit>
}