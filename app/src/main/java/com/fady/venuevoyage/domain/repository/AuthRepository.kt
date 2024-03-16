package com.fady.venuevoyage.domain.repository

import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.presentation.utils.common.Resource


interface AuthRepository {

    fun saveUser(userProfile: UserProfile): Resource<Unit>

    fun login(email: String, password: String): Resource<Unit>

    fun isLoggedIn(): Resource<Boolean>
}