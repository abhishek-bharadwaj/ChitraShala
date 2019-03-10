package com.abhishek.chitrashala.data.network

import com.abhishek.chitrashala.data.models.AboutApiResponse
import com.abhishek.chitrashala.data.models.PostApiResponse
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
import retrofit2.http.Query
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

    fun getRedditData(subreddit: String, limit: Int = 5) =
        apiService.getRedditData(subreddit, limit)

    fun getRedditData(subreddit: String, after: String, limit: Int = 5) =
        apiService.getRedditData(subreddit, after, limit)

    fun getSubredditInfo(subreddit: String) = apiService.getSubredditInfo(subreddit)

    interface ApiService {
        @GET("{subreddit}/new.json")
        fun getRedditData(@Path("subreddit") subreddit: String,
                          @Query("limit") limit: Int): Single<Response<PostApiResponse>>

        @GET("{subreddit}/new.json")
        fun getRedditData(@Path("subreddit") subreddit: String,
                          @Query("after") after: String,
                          @Query("limit") limit: Int)
                : Single<Response<PostApiResponse>>

        @GET("{subreddit}/about.json")
        fun getSubredditInfo(@Path("subreddit") subreddit: String): Single<Response<AboutApiResponse>>
    }
}