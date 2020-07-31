package com.example.imagegallery.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.imagegallery.R
import com.example.imagegallery.databinding.DialogFragmentImageInfoBinding
import com.example.imagegallery.viewmodel.ImageInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ImageInfoDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFragmentImageInfoBinding
    private val args: ImageInfoDialogFragmentArgs by navArgs()

    private val imageInfoViewModel: ImageInfoViewModel by viewModel { parametersOf(args.image) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<DialogFragmentImageInfoBinding>(
            inflater, R.layout.dialog_fragment_image_info, container, false
        ).apply {
            viewModel = imageInfoViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setUi()
        setButtonsClickListener()

        return binding.root
    }

    /**
     * UI configuration.
     */
    private fun setUi() {
        val image = imageInfoViewModel.image

        with(binding) {
            imageSubreddit.text = getString(R.string.subreddit_name_prefixed, image.subreddit)
            imageTitle.text = image.title
        }
    }

    /**
     * Buttons click listener configuration.
     */
    private fun setButtonsClickListener() {
        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                imageInfoViewModel.saveImage()
                this@ImageInfoDialogFragment.dismiss()
            }
        }

        binding.removeButton.setOnClickListener {
            lifecycleScope.launch {
                imageInfoViewModel.removeImage()
                this@ImageInfoDialogFragment.dismiss()
            }
        }
    }
}