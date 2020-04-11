package com.lekalina.kotlin.sample

import com.lekalina.kotlin.sample.data.Album
import com.lekalina.kotlin.sample.data.Images
import com.lekalina.kotlin.sample.viewmodel.AlbumItemViewModel
import com.lekalina.kotlin.sample.viewmodel.GalleryItemViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    private lateinit var galleryViewModel: GalleryItemViewModel
    private lateinit var albumViewModel: AlbumItemViewModel

    private lateinit var mockImage: Images
    private lateinit var mockAlbum: Album
    private var mockImageList: MutableList<Images> = ArrayList()

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
    }

    @Test
    fun testGalleryItemViewModel() {
        galleryViewModel = GalleryItemViewModel(mockAlbum)
        assertNotNull(this.galleryViewModel.album)
        assertEquals("https://www.cats.com", this.galleryViewModel.getAlbumCoverUrl())
    }

    @Test
    fun testAlbumItemViewModel() {
        albumViewModel = AlbumItemViewModel(mockImage)
        assertNotNull(this.albumViewModel.image)
        assertEquals("video", this.albumViewModel.typeVideo)
    }
}
