package com.lekalina.kotlin.sample.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lekalina.kotlin.sample.R
import com.lekalina.kotlin.sample.data.Album
import com.lekalina.kotlin.sample.data.GallerySearchResponse
import com.lekalina.kotlin.sample.databinding.ActivityMainBinding
import com.lekalina.kotlin.sample.repos.GalleryRepository
import com.lekalina.kotlin.sample.utils.Utils
import com.lekalina.kotlin.sample.viewmodel.GalleryItemViewModel
import com.lekalina.kotlin.sample.viewmodel.GalleryViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainGalleryActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable
    lateinit var binding: ActivityMainBinding
    private lateinit var model: GalleryViewModel

    @Override
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        model = GalleryViewModel(GalleryRepository())
        binding.viewModel = model
        binding.lifecycleOwner = this

        list.layoutManager = LinearLayoutManager(this)

        model.searchResult.observeForever { result ->
            updateAdapter(result)
            model.setShowLoading(false)
            model.setAlbumList(result.data)
            Utils.hideSoftKeyBoard(this@MainGalleryActivity, binding.root)
        }
    }

    override fun onStart() {
        super.onStart()

        disposable = createTextChangeObservable()
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Utils.hideSoftKeyBoard(this@MainGalleryActivity, binding.root)
                model.setShowLoading(true)
                model.searchQuery.value = it
            }
    }

    @Override
    override fun onStop() {
        super.onStop()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    /**
     * Updates the adapter with new results after search is completed
     * @param result - network search result
     */
    private fun updateAdapter(result: GallerySearchResponse) {

        val itemViewModels: ArrayList<GalleryItemViewModel> = ArrayList()

        result.data.forEach { album ->
            if(album.images_count > 0) { // only albums with photos are included
                itemViewModels.add(GalleryItemViewModel(album))
            }
        }

        list.adapter = GalleryAdapter(itemViewModels, object: GalleryAdapter.OnItemClickListener {
            override fun onItemClick(view: View, item: Album) {
                val intent = Intent(this@MainGalleryActivity, AlbumActivity::class.java)
                intent.putExtra("hash", item.id)
                startActivity(intent)
            }
        })
    }

    /**
     * Creates an Observable on TextWatcher that emits a string if the user has entered
     * more than 2 characters into the edit text, has not typed a character in 700ms, and
     * the current string change is not equal to the previous string
     * @return Observable<String>
     */
    private fun createTextChangeObservable(): Observable<String> {

        val textChangeObservable = Observable.create<String> { emitter ->

            val textWatcher = object : TextWatcher {

                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    if(count < after) {
                        s?.toString()?.let { emitter.onNext(it) }
                    }
                }
            }

            queryEditText.addTextChangedListener(textWatcher)

            emitter.setCancellable {
                queryEditText.removeTextChangedListener(textWatcher)
            }
        }

        return textChangeObservable
                // 2 letter minimum for search filter
                .filter { it.length >= 2 }
                // 700 milliseconds buffer so user can finish typing before auto-search kicks off
                .debounce(700, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
    }

}