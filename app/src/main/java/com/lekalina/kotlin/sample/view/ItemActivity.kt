package com.lekalina.kotlin.sample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lekalina.kotlin.sample.R
import com.lekalina.kotlin.sample.databinding.ItemViewBinding
import com.lekalina.kotlin.sample.repos.ItemRepository
import com.lekalina.kotlin.sample.viewmodel.ItemViewModel

class ItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ItemViewBinding = DataBindingUtil.setContentView(this, R.layout.item_view)
        val model = ItemViewModel(ItemRepository())
        binding.viewmodel = model
        binding.lifecycleOwner = this

        model.setItemHash(intent.getStringExtra("hash")!!)

        model.result.observeForever { result ->
            if(result.data.type.contains("video", ignoreCase = true)) {
                val fragment: VideoFragment = VideoFragment.newInstance(result.data.link)
                supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
            }
            else {
                val fragment: PhotoFragment = PhotoFragment.newInstance(result.data.link)
                supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
            }
            model.setShowLoading(false)
            model.setDescription(result.data)
        }
    }
}