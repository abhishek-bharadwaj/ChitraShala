package com.abhishek.chitrashala.data.network

import com.abhishek.chitrashala.data.models.RedditData
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


object Api {

    private val apiService by lazy {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()

        Retrofit.Builder().baseUrl("https://www.reddit.com/r/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(Api.ApiService::class.java)
    }

    fun getRedditData(subreddit: String) = apiService.getRedditData(subreddit)

    interface ApiService {
        @GET("{subreddit}/new.json?limit=20")
        fun getRedditData(@Path("subreddit") subreddit: String): Single<Response<RedditData>>
    }
}