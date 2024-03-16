package com.fady.venuevoyage.data.datasource.remote

import com.fady.venuevoyage.domain.service.ClientService
import com.fady.venuevoyage.presentation.utils.base.BaseRemoteDataSource
import javax.inject.Inject

class VenuesRemoteDataSource @Inject constructor(
    private val apiService: ClientService
) : BaseRemoteDataSource() {


    suspend fun getVenues(location: Array<Double>) = safeApiCall {
        apiService.getVenues(
            location = "${location[0]},${location[1]}"
        )
    }


}