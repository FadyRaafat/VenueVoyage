package com.fady.venuevoyage.data.models

data class UserProfile(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val age: Int? = null,
    val password: String? = null
)