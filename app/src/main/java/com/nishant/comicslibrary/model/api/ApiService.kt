package com.nishant.comicslibrary.model.api

import com.nishant.comicslibrary.BuildConfig
import com.nishant.comicslibrary.getHash
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    //private const val BASE_URL = "https://gateway.marvel.com:443/v1/public"
    private const val BASE_URL = "https://gateway.marvel.com/v1/public"

    private fun getRetrofit(): Retrofit {
        val ts = System.currentTimeMillis().toString()
        val apiSecret = BuildConfig.MARVEL_SECRET
        val apiKey = BuildConfig.MARVEL_KEY
        val hash = getHash(ts, apiSecret, apiKey)

        val clientInterceptor = Interceptor {chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("hash", hash)
                .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)

        }
        val client = okhttp3.OkHttpClient.Builder()
            .addInterceptor(clientInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: MarvelApi = getRetrofit().create(MarvelApi::class.java)
}