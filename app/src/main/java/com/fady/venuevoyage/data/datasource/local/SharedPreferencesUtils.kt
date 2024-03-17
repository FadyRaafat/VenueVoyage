package com.fady.venuevoyage.data.datasource.local

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken

internal inline fun <reified T> SharedPreferences?.processStoredObject(
    key: String, default: String = ""
): T {
    val objectString = getSafeString(key, default)
    val typeToken = object : TypeToken<T>() {}.type
    return LocalDataSource.gson.fromJson(objectString, typeToken)
}

internal inline fun <reified T> SharedPreferences?.storeObject(key: String, value: T) {
    if (this == null) return
    val objectInJson = LocalDataSource.gson.toJson(value as Any)
    edit().putString(key, objectInJson).apply()
}

internal fun SharedPreferences?.getSafeString(key: String, default: String = ""): String {
    return this?.getString(key, default) ?: default
}
