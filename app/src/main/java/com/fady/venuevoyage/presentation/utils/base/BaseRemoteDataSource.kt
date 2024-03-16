package com.fady.venuevoyage.presentation.utils.base

import com.fady.venuevoyage.presentation.utils.common.FailureStatus
import com.fady.venuevoyage.presentation.utils.common.Resource
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

open class BaseRemoteDataSource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        try {
            return Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    when (throwable.code()) {
                        400 -> {
                            return Resource.Failure(FailureStatus.API_FAIL, throwable.code(), "400")
                        }

                        422 -> {
                            return Resource.Failure(FailureStatus.API_FAIL, throwable.code(), "422")
                        }

                        404 -> {
                            return Resource.Failure(FailureStatus.API_FAIL, throwable.code(), "404")
                        }

                        else -> {
                            return if (throwable.response()?.errorBody()!!.charStream().readText()
                                    .isEmpty()
                            ) {
                                Resource.Failure(FailureStatus.API_FAIL, throwable.code())
                            } else {
                                Resource.Failure(
                                    FailureStatus.API_FAIL, throwable.code()
                                )
                            }
                        }
                    }
                }

                is UnknownHostException -> {
                    return Resource.Failure(FailureStatus.NO_INTERNET)
                }

                is ConnectException -> {
                    return Resource.Failure(FailureStatus.NO_INTERNET)
                }

                else -> {
                    return Resource.Failure(FailureStatus.OTHER)
                }
            }
        }
    }

}