package com.lekalina.kotlin.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lekalina.kotlin.sample.databinding.PhotoViewBinding
import com.lekalina.kotlin.sample.viewmodel.PhotoViewModel

class PhotoFragment: Fragment() {

    companion object {

        const val PATH = "path"

        fun newInstance(path: String): PhotoFragment {
            val fragment = PhotoFragment()

            val bundle = Bundle().apply {
                putString(PATH, path)
            }

            fragment.arguments = bundle

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = PhotoViewBinding.inflate(layoutInflater, container, false)
        val url: String = arguments?.getString(PATH)!!
        binding.viewmodel = PhotoViewModel(url)
        return binding.root
    }


}