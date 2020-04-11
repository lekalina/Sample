package com.lekalina.kotlin.sample.repos

import com.lekalina.kotlin.sample.api.RestCaller

class GalleryRepository {

    suspend fun getGalleryAlbums(q: String) = RestCaller().getGalleryAlbums(q)
}