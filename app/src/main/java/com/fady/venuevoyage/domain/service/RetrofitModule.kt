package com.fady.venuevoyage.domain.service

import com.fady.venuevoyage.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val REQUEST_TIME_OUT: Long = 60

    private const val HEADER_ACCEPT = "Accept"
    private const val HEADER_CONTENT_TYPE = "Content-Type"
    private const val HEADER_ACCEPT_VALUE = "application/json"

    @Provides
    @Singleton
    fun provideHeadersInterceptor() = Interceptor { chain ->
        var newRequest = chain.request()

        val url = newRequest.url.newBuilder().build()

        newRequest = newRequest.newBuilder().addHeader(HEADER_ACCEPT, HEADER_ACCEPT_VALUE)
            .addHeader(HEADER_CONTENT_TYPE, HEADER_ACCEPT_VALUE).url(url).build()

        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headersInterceptor: Interceptor, logging: HttpLoggingInterceptor
    ): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder().readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headersInterceptor).addNetworkInterceptor(logging).build()
        } else {
            OkHttpClient.Builder().readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headersInterceptor).build()
        }
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().serializeNulls().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(BuildConfig.BASE_URL)
            .build()
}