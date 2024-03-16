package com.fady.venuevoyage.presentation.utils.common


sealed class Resource<out T> {

  data class Error(val error: Throwable?) : Resource<Nothing>()
  data class Success<out T>(val value: T) : Resource<T>()

  class Failure(
      val failureStatus: FailureStatus,
      val code: Int? = null,
      val message: String? = null
  ) : Resource<Nothing>()

  object Loading : Resource<Nothing>()
  object Empty : Resource<Nothing>()

  companion object {
    fun <T> success(data: T): Resource<T> = Success(data)
    fun <T> empty(): Resource<T> = Empty
  }
}