package com.lekalina.kotlin.sample.api

import com.lekalina.kotlin.sample.data.AlbumResponse
import com.lekalina.kotlin.sample.data.GallerySearchResponse
import com.lekalina.kotlin.sample.data.ItemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    @GET("3/gallery/search/viral/month")
    suspend fun getGalleryAlbums(
        @Query("q_all") q: String,
        @Query("q_type") type: String = "album"
    ): GallerySearchResponse

    @GET("3/gallery/album/{hash}")
    suspend fun getAlbum(@Path("hash") albumHash: String): AlbumResponse

    @GET("3/image/{hash}")
    suspend fun getItem(@Path("hash") itemHash: String): ItemResponse
}