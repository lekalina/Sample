package com.lekalina.kotlin.sample.viewmodel


import com.lekalina.kotlin.sample.data.Album


class GalleryItemViewModel(val album: Album): BaseViewModel() {

    fun getAlbumCoverUrl(): String {
        return if(album.images_count < 1) "" else album.images[0].link
    }
}