package com.lekalina.kotlin.sample.repos

import com.lekalina.kotlin.sample.api.RestCaller

class AlbumRepository {

    suspend fun getAlbum(albumHash: String) = RestCaller().getAlbum(albumHash)
}