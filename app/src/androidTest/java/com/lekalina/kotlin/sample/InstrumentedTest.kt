package com.lekalina.kotlin.sample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lekalina.kotlin.sample.data.Album
import com.lekalina.kotlin.sample.data.Images
import com.lekalina.kotlin.sample.repos.AlbumRepository
import com.lekalina.kotlin.sample.repos.GalleryRepository
import com.lekalina.kotlin.sample.repos.ItemRepository
import com.lekalina.kotlin.sample.viewmodel.AlbumViewModel
import com.lekalina.kotlin.sample.viewmodel.GalleryViewModel
import com.lekalina.kotlin.sample.viewmodel.ItemViewModel
import com.lekalina.kotlin.sample.viewmodel.PhotoViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runners.JUnit4

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class InstrumentedTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var photoViewModel: PhotoViewModel

    private lateinit var mockImage: Images
    private lateinit var mockAlbum: Album
    private var mockImageList: MutableList<Images> = ArrayList()
    private var mockAlbumList: MutableList<Album> = ArrayList()

    @Before
    fun setUp() {

        mockImage = Images(
            "1",
            "cat_image",
            "https://www.cats.com",
            false,
            "image/jpg"
        )

        mockImageList.add(mockImage)

        mockAlbum = Album(
            "1",
            "cats",
            "1",
            mockImageList.size,
            mockImageList,
            1
        )

        mockAlbumList.add(mockAlbum)
    }

    @Test
    fun testGalleryViewModel() {

        galleryViewModel = GalleryViewModel(GalleryRepository())
        assertEquals("cats", galleryViewModel.searchQuery.value)

        this.galleryViewModel.setAlbumList(mockAlbumList)
        assertEquals(1, this.galleryViewModel.albumSize.value)
        assertEquals(mockAlbumList, this.galleryViewModel.albumList.value)

        this.galleryViewModel.setShowLoading(true)
        assertEquals("show", this.galleryViewModel.showLoading.value)

        this.galleryViewModel.setShowLoading(false)
        assertEquals("", this.galleryViewModel.showLoading.value)
    }

    @Test
    fun testAlbumViewModel() {

        albumViewModel = AlbumViewModel(AlbumRepository())

        this.albumViewModel.setAlbumHash(mockAlbum.id)
        assertEquals("1", this.albumViewModel.albumHash.value)

        this.albumViewModel.setShowLoading(true)
        assertEquals("show", this.albumViewModel.showLoading.value)

        this.albumViewModel.setShowLoading(false)
        assertEquals("", this.albumViewModel.showLoading.value)
    }

    @Test
    fun testItemViewModel() {

        itemViewModel = ItemViewModel(ItemRepository())

        this.itemViewModel.setDescription(mockImage)
        assertEquals("cat_image", this.itemViewModel.description.value)

        this.itemViewModel.setItemHash(mockImage.id)
        assertEquals("1", this.itemViewModel.itemHash.value)

        this.itemViewModel.setShowLoading(true)
        assertEquals("show", this.itemViewModel.showLoading.value)

        this.itemViewModel.setShowLoading(false)
        assertEquals("", this.itemViewModel.showLoading.value)
    }

    @Test
    fun testPhotoViewModel() {
        photoViewModel = PhotoViewModel(mockImage.link)
        assertEquals("https://www.cats.com", photoViewModel.url)
    }
}
