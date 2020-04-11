package com.lekalina.kotlin.sample.data


data class GallerySearchResponse(val data: List<Album>)

data class AlbumResponse(val data: Album)

data class ItemResponse(val data: Images)

data class Album(
    val id: String,
    val title: String,
    val cover: String,
    val images_count: Int,
    val images: List<Images>,
    var views: Int
)

data class Images(
    val id: String,
    val description: String,
    val link: String,
    val animated: Boolean,
    val type: String
)