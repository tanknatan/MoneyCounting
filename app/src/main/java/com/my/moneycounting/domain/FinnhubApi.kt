package com.my.moneycounting.domain

import com.my.moneycounting.data.NewsArticle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//interface FinnhubApi {
//    @GET("api/v1/news?category=general&token=YOUR_API_KEY")
//    suspend fun getMarketNews(): List<NewsArticle>
//}
//
//object RetrofitInstance {
//    val api: FinnhubApi by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://finnhub.io/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(FinnhubApi::class.java)
//    }
//}

interface FinnhubApi {

    object RetrofitInstance {
        private const val BASE_URL = "https://finnhub.io/"

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: FinnhubApi by lazy {
            retrofit.create(FinnhubApi::class.java)
        }
    }
    @GET("api/v1/news")
    suspend fun getMarketNews(
        @Query("category") category: String = "general",
        @Query("token") apiKey: String
    ): List<NewsArticle>
}
