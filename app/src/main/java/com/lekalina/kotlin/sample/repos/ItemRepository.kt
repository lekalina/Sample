package com.lekalina.kotlin.sample.repos

import com.lekalina.kotlin.sample.api.RestCaller

class ItemRepository {

    suspend fun getItem(itemHash: String) = RestCaller().getItem(itemHash)
}