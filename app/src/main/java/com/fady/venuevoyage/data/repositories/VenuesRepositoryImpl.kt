package com.fady.venuevoyage.data.repositories

import com.fady.venuevoyage.data.datasource.remote.VenuesRemoteDataSource
import com.fady.venuevoyage.data.models.VenuesResponse
import com.fady.venuevoyage.domain.repository.VenuesRepository
import com.fady.venuevoyage.presentation.utils.common.Resource
import javax.inject.Inject

class VenuesRepositoryImpl @Inject constructor(
    private val venuesRemoteDataSource: VenuesRemoteDataSource
) : VenuesRepository {

    override suspend fun getVenues(location: Array<Double>): Resource<VenuesResponse> {
        return venuesRemoteDataSource.getVenues(location)
    }

}