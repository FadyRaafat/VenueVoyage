package com.fady.venuevoyage.domain.usecases.home

import com.fady.venuevoyage.data.models.VenuesResponse
import com.fady.venuevoyage.domain.repository.VenuesRepository
import com.fady.venuevoyage.presentation.utils.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetVenuesUseCase @Inject constructor(
    private val venuesRepository: VenuesRepository
) {
    operator fun invoke(location: Array<Double>): Flow<Resource<VenuesResponse>> =
        flow {
            emit(Resource.Loading)
            emit(venuesRepository.getVenues(location))
        }.flowOn(Dispatchers.IO)
}
