package com.fady.venuevoyage.domain.service

import com.fady.venuevoyage.BuildConfig
import com.fady.venuevoyage.data.models.VenuesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientService {

    @GET("venues/search")
    suspend fun getVenues(
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Query("v") version: String = BuildConfig.VERSION,
        @Query("ll") location: String,
    ): VenuesResponse

}