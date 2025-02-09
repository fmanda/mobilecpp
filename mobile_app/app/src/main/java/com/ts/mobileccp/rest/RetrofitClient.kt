package com.ts.mobileccp.rest

import com.ts.mobileccp.global.AppVariable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private var BASE_URL = AppVariable.setting.api_url

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log request and response body
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(client)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

//    val apiService: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }


        private var _apiService: ApiService? = null


        val apiService: ApiService
            get() {
                if (_apiService == null) {
                    _apiService = createApiService(BASE_URL)
                }
                return _apiService!!
            }


        private fun createApiService(baseUrl: String): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }


        fun updateRetrofitBaseURL(newBaseUrl: String) {
            BASE_URL = newBaseUrl
            _apiService = createApiService(BASE_URL)
        }
}