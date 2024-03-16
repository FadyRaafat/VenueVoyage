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

internal inline fun <reified T> SharedPreferences?.processStoredBoolean(
    key: String, default: Boolean
): T {
    val objectString = getString(key) ?: default.toString()
    val typeToken = object : TypeToken<T>() {}.type
    return LocalDataSource.gson.fromJson(objectString, typeToken)
}

internal inline fun <reified T> SharedPreferences?.processStoredInt(key: String, default: Int): T {
    val objectString = getString(key) ?: default.toString()
    val typeToken = object : TypeToken<T>() {}.type
    return LocalDataSource.gson.fromJson(objectString, typeToken)
}

internal inline fun <reified T> SharedPreferences?.storeObject(key: String, value: T) {
    if (this == null) return
    val objectInJson = LocalDataSource.gson.toJson(value as Object)
    edit().putString(key, objectInJson).apply()
}

internal fun SharedPreferences?.getSafeString(key: String, default: String = ""): String {
    return this?.getString(key, default) ?: default
}

internal fun SharedPreferences?.getString(key: String): String? {
    return this?.getString(key, null)
}

internal fun SharedPreferences?.setString(key: String, value: String?) {
    if (this == null) return
    edit().putString(key, value).apply()
}


internal fun SharedPreferences?.saveBoolean(key: String, value: Boolean) {
    if (this == null) return
    edit().putBoolean(key, value).apply()
}

internal fun SharedPreferences?.saveLong(key: String, value: Long) {
    if (this == null) return
    edit().putLong(key, value).apply()
}

internal fun SharedPreferences?.saveInt(key: String, value: Int) {
    if (this == null) return
    edit().putInt(key, value).apply()
}

internal fun SharedPreferences?.getSafeBoolean(key: String): Boolean {
    return this?.getBoolean(key, false) ?: false
}

internal fun SharedPreferences?.getSafeLong(key: String, default: Long): Long {
    return this?.getLong(key, default) ?: default
}

internal fun SharedPreferences?.getSafeInt(key: String, default: Int): Int {
    return this?.getInt(key, default) ?: default
}