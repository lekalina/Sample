package com.lekalina.kotlin.sample.viewmodel

import androidx.lifecycle.*
import com.lekalina.kotlin.sample.data.Album
import com.lekalina.kotlin.sample.data.GallerySearchResponse
import com.lekalina.kotlin.sample.repos.GalleryRepository
import kotlinx.coroutines.Dispatchers

class GalleryViewModel(private val repository: GalleryRepository): ViewModel() {

    private val defaultQuery = "cats"

    var searchQuery = MutableLiveData<String>()
    var showLoading = MutableLiveData<String>()
    var albumList = MutableLiveData<List<Album>>()
    var albumSize = MutableLiveData<Int>()

    val searchResult = searchQuery.switchMap { query ->
        liveData(Dispatchers.IO) {
            emit(repository.getGalleryAlbums(query))
        }
    }

    init {
        searchQuery.value = defaultQuery
        setShowLoading(false)
    }

    fun setAlbumList(list: List<Album>) {
        albumList.value = list
        albumSize.value = list.size
    }

    fun setShowLoading(isLoading: Boolean) {
        if(isLoading) {
            showLoading.value = "show"
        }
        else {
            showLoading.value = ""
        }
    }
}