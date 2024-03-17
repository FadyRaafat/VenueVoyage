package com.fady.venuevoyage.data.models

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName


data class VenuesResponse(
    @SerializedName("response") val response: Response?
)
data class Response(
    @SerializedName("venues") val venues: List<Venue>?
)
data class Venue(
    @SerializedName("categories") val categories: List<Category>?,
    @SerializedName("id") val id: String?,
    @SerializedName("location") val location: Location?,
    @SerializedName("name") val name: String?
)
data class Category(
    @SerializedName("icon") val icon: Icon?,
    @SerializedName("name") val name: String?,
)
data class Icon(
    @SerializedName("prefix") val prefix: String?, @SerializedName("suffix") val suffix: String?
) {
    fun getIconUrl(): String {
        return "${prefix}44${suffix}"
    }

}
data class Location(
    @SerializedName("formattedAddress") val formattedAddress: List<String?>?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?,
) {
    fun getFormattedAddress(): String {
        return formattedAddress?.joinToString(separator = ", ") ?: ""
    }

    fun getLatLng(): LatLng {
        return LatLng(lat ?: 0.0, lng ?: 0.0)
    }

}


