package com.fady.venuevoyage.data.models

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName


data class VenuesResponse(
    @SerializedName("meta") val meta: Meta?, @SerializedName("response") val response: Response?
)

data class Meta(
    @SerializedName("code") val code: Int?, @SerializedName("requestId") val requestId: String?
)


data class Response(
    @SerializedName("confident") val confident: Boolean?,
    @SerializedName("venues") val venues: List<Venue>?
)

data class Venue(
    @SerializedName("categories") val categories: List<Category>?,
    @SerializedName("createdAt") val createdAt: Int?,
    @SerializedName("id") val id: String?,
    @SerializedName("location") val location: Location?,
    @SerializedName("name") val name: String?
)


data class Category(
    @SerializedName("categoryCode") val categoryCode: Int?,
    @SerializedName("icon") val icon: Icon?,
    @SerializedName("id") val id: String?,
    @SerializedName("mapIcon") val mapIcon: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("pluralName") val pluralName: String?,
    @SerializedName("primary") val primary: Boolean?,
    @SerializedName("shortName") val shortName: String?
)

data class Icon(
    @SerializedName("prefix") val prefix: String?, @SerializedName("suffix") val suffix: String?
) {
    fun getIconUrl(): String {
        return "${prefix}44${suffix}"
    }

}


data class Location(
    @SerializedName("address") val address: String?,
    @SerializedName("cc") val cc: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("crossStreet") val crossStreet: String?,
    @SerializedName("distance") val distance: Int?,
    @SerializedName("formattedAddress") val formattedAddress: List<String?>?,
    @SerializedName("labeledLatLngs") val labeledLatLngs: List<LabeledLatLng?>?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?,
    @SerializedName("postalCode") val postalCode: String?,
    @SerializedName("state") val state: String?
) {
    fun getFormattedAddress(): String {
        return formattedAddress?.joinToString(separator = ", ") ?: ""
    }

    fun getLatLng(): LatLng {
        return LatLng(lat ?: 0.0, lng ?: 0.0)
    }

}

data class LabeledLatLng(
    @SerializedName("label") val label: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?
)



