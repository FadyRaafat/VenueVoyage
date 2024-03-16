package com.fady.venuevoyage.domain.repository

import com.fady.venuevoyage.data.models.VenuesResponse
import com.fady.venuevoyage.presentation.utils.common.Resource

interface VenuesRepository {

    suspend fun getVenues(location: Array<Double>) : Resource<VenuesResponse>
}