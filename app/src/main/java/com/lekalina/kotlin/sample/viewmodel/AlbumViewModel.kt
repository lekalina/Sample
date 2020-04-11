package com.lekalina.kotlin.sample.viewmodel

import androidx.lifecycle.*
import com.lekalina.kotlin.sample.repos.AlbumRepository
import kotlinx.coroutines.Dispatchers

class AlbumViewModel(private val repository: AlbumRepository): ViewModel() {

    var albumHash = MutableLiveData<String>()
    var showLoading = MutableLiveData<String>()

    val result = albumHash.switchMap { hash ->
        liveData(Dispatchers.IO) {
            emit(repository.getAlbum(hash))
        }
    }

    init {
        setShowLoading(false)
    }

    fun setShowLoading(isLoading: Boolean) {
        if(isLoading) {
            showLoading.value = "show"
        }
        else {
            showLoading.value = ""
        }
    }

    fun setAlbumHash(hash: String) {
        albumHash.value = hash
    }
}