package com.lekalina.kotlin.sample.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.lekalina.kotlin.sample.R
import com.lekalina.kotlin.sample.data.AlbumResponse
import com.lekalina.kotlin.sample.data.Images
import com.lekalina.kotlin.sample.databinding.ActivityAlbumBinding
import com.lekalina.kotlin.sample.repos.AlbumRepository
import com.lekalina.kotlin.sample.viewmodel.AlbumItemViewModel
import com.lekalina.kotlin.sample.viewmodel.AlbumViewModel
import kotlinx.android.synthetic.main.activity_main.*

class AlbumActivity: AppCompatActivity() {

    @Override
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAlbumBinding = DataBindingUtil.setContentView(this, R.layout.activity_album)
        val model = AlbumViewModel(AlbumRepository())
        binding.viewModel = model
        binding.lifecycleOwner = this

        list.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        model.result.observeForever { result ->
            updateAdapter(result)
            model.setShowLoading(false)
        }

        model.setShowLoading(true)
        model.setAlbumHash(intent.getStringExtra("hash")!!)
    }

    private fun updateAdapter(result: AlbumResponse) {

        val itemViewModels: ArrayList<AlbumItemViewModel> = ArrayList()

        result.data.images.forEach { item ->
            itemViewModels.add(AlbumItemViewModel(item))
        }

        list.adapter = AlbumAdapter(itemViewModels, object: AlbumAdapter.OnItemClickListener {
            override fun onItemClick(view: View, item: Images) {
                val intent = Intent(this@AlbumActivity, ItemActivity::class.java)
                intent.putExtra("hash", item.id)
                startActivity(intent)
            }
        })
    }

}