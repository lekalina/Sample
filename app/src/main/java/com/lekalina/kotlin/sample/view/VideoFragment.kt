package com.lekalina.kotlin.sample.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.lekalina.kotlin.sample.databinding.VideoViewBinding

class VideoFragment: Fragment() {

    lateinit var binding: VideoViewBinding
    private lateinit var mediaController: MediaController
    private var url: String? = null

    companion object {

        const val PATH = "path"

        fun newInstance(path: String): VideoFragment {
            val fragment = VideoFragment()

            val bundle = Bundle().apply {
                putString(PATH, path)
            }

            fragment.arguments = bundle

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = VideoViewBinding.inflate(layoutInflater, container, false)
        url = arguments?.getString(PATH)
        configureVideo()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mediaController = MediaController(context)
    }

    private fun configureVideo() {
        mediaController.setAnchorView(view)
        binding.videoPlayer.setMediaController(mediaController)
        binding.videoPlayer.setVideoPath(url)
        binding.videoPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        binding.videoPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.videoPlayer.resume()
    }
}