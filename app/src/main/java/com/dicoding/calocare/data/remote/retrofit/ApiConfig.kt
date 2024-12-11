package com.dicoding.calocare.data.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://calocare-266542065750.asia-southeast2.run.app")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor { chain ->
                            val originalRequest = chain.request()
                            val request = originalRequest.newBuilder()
                                .method(originalRequest.method, originalRequest.body)
                                .build()
                            chain.proceed(request)
                        }
                        .build()
                )
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}