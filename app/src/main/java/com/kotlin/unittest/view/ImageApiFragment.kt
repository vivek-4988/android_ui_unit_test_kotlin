package com.kotlin.unittest.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.unittest.R
import com.kotlin.unittest.adapter.ImageRecyclerAdapter
import com.kotlin.unittest.databinding.FragmentImageApiBinding
import com.kotlin.unittest.viewmodel.ArtViewModel
import com.kotlin.util.Status
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel
    private var _binding: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        val binding = FragmentImageApiBinding.bind(view)
        _binding = binding

        var job: Job? = null

        _binding!!.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }


        subscribeToObservers()

        _binding!!.imageRecyclerView.adapter = imageRecyclerAdapter
        _binding!!.imageRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    _binding!!.progressBar.visibility = View.GONE
                }

                Status.ERROR -> _binding!!.progressBar.visibility = View.GONE
                Status.LOADING -> _binding!!.progressBar.visibility = View.VISIBLE
            }
        }
    }
}