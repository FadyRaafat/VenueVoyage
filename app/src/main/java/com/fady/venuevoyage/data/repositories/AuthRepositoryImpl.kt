package com.fady.venuevoyage.data.repositories

import com.fady.venuevoyage.data.datasource.local.LocalDataSource
import com.fady.venuevoyage.data.models.UserProfile
import com.fady.venuevoyage.domain.repository.AuthRepository
import com.fady.venuevoyage.presentation.utils.common.FailureStatus
import com.fady.venuevoyage.presentation.utils.common.Resource
import java.util.Random
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
) : AuthRepository {


    override fun saveUser(userProfile: UserProfile): Resource<Unit> {
        LocalDataSource.userProfile = userProfile
        return Resource.Success(Unit)
    }

    override fun login(email: String, password: String): Resource<Unit> {
        return if (LocalDataSource.isCorrectEmailAndPassword(
                email, password
            )
        ) {
            LocalDataSource.token = Random().nextInt().toString()
            Resource.Success(Unit)
        } else {
            Resource.Failure(
                FailureStatus.API_FAIL, code = 400, message = "Invalid email or password"
            )
        }
    }

    override fun isLoggedIn(): Resource<Boolean> {
        return Resource.Success(LocalDataSource.isUserLoggedIn())
    }
}
