package com.fady.venuevoyage.application

import android.app.Application
import com.fady.venuevoyage.data.datasource.local.LocalDataSource
import com.fady.venuevoyage.data.datasource.local.LocalDataSource.FILE_PREFERENCES
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LocalDataSource.initialize(getSharedPreferences(FILE_PREFERENCES, MODE_PRIVATE))
    }
}