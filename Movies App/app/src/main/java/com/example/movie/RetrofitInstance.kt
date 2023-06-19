package com.example.movie

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        fun getRetrofitInstance(): Retrofit {
            val interceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOTNlYmM5ZmI1ZTViZmJjYmFkNDExNGEyZGEyMzE0MCIsInN1YiI6IjYzN2UyNWM5ZTgxMzFkMDBlNmE4ZDBlYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4v3-UYZQR77_SY2jS_9FVhZBfKS6qWEN3ieC7u_PLr0")
                    .build()
                chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okHttpClient)
                .build()
        }
    }
}