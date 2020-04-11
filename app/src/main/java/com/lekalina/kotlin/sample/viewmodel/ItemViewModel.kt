package com.lekalina.kotlin.sample.viewmodel

import androidx.lifecycle.*
import com.lekalina.kotlin.sample.data.Images
import com.lekalina.kotlin.sample.data.ItemResponse
import com.lekalina.kotlin.sample.repos.ItemRepository
import kotlinx.coroutines.Dispatchers

class ItemViewModel(private val repository: ItemRepository): ViewModel() {

    var itemHash = MutableLiveData<String>()
    var showLoading = MutableLiveData<String>()
    var description = MutableLiveData<String>()

    val result: LiveData<ItemResponse> = itemHash.switchMap { hash ->
        liveData(Dispatchers.IO) {
            emit(repository.getItem(hash))
        }
    }

    init {
        setShowLoading(false)
    }

    fun setShowLoading(isLoading: Boolean) {
        if(isLoading) {
            showLoading?.value = "show"
        }
        else {
            showLoading?.value = ""
        }
    }

    fun setDescription(image: Images) {
        if(image?.description != null) {
            description.value = image.description
        }
        else {
            description.value = ""
        }
    }

    fun setItemHash(hash: String) {
        itemHash.value = hash
    }
}