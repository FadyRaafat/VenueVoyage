package com.fady.venuevoyage.data.di

import android.app.Application
import com.fady.venuevoyage.data.datasource.remote.VenuesRemoteDataSource
import com.fady.venuevoyage.data.repositories.AuthRepositoryImpl
import com.fady.venuevoyage.data.repositories.ProfileRepositoryImpl
import com.fady.venuevoyage.data.repositories.VenuesRepositoryImpl
import com.fady.venuevoyage.domain.repository.AuthRepository
import com.fady.venuevoyage.domain.repository.ProfileRepository
import com.fady.venuevoyage.domain.repository.VenuesRepository
import com.fady.venuevoyage.domain.service.ClientService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository =
        AuthRepositoryImpl()

    @Provides
    @Singleton
    fun provideProfileRepository(): ProfileRepository =
        ProfileRepositoryImpl()


    @Provides
    @Singleton
    fun provideVenuesRepository(venuesRemoteDataSource : VenuesRemoteDataSource): VenuesRepository =
        VenuesRepositoryImpl(venuesRemoteDataSource)

    @Provides
    @Singleton
    fun provideProductServices(retrofit: Retrofit): ClientService =
        retrofit.create(ClientService::class.java)

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
}