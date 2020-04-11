package com.lekalina.kotlin.sample.api

import com.lekalina.kotlin.sample.data.AlbumResponse
import com.lekalina.kotlin.sample.data.GallerySearchResponse
import com.lekalina.kotlin.sample.data.ItemResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestCaller {

    private val service: RestApi

    companion object {
        const val BASE_URL = "https://api.imgur.com/"
    }

    init {

        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("Authorization", "Client-ID 2d086962f60c89e")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        service = retrofit.create(RestApi::class.java)
    }

    suspend fun getGalleryAlbums(q: String): GallerySearchResponse {
        return service.getGalleryAlbums(q)
    }

    suspend fun getAlbum(albumHash: String): AlbumResponse {
        return service.getAlbum(albumHash)
    }

    suspend fun getItem(itemHash: String): ItemResponse {
        return service.getItem(itemHash)
    }

}