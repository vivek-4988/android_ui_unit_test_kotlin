package com.kotlin.unittest.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.unittest.R
import com.kotlin.unittest.adapter.ArtRecycleAdapter
import com.kotlin.unittest.databinding.FragmentArtsBinding
import com.kotlin.unittest.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    private val artRecyclerAdapter: ArtRecycleAdapter
) : Fragment(R.layout.fragment_arts) {

    private var _binding: FragmentArtsBinding? = null
    lateinit var viewModel: ArtViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        _binding = FragmentArtsBinding.bind(view)
        val bind = _binding!!

        subscribeToObservers()

        bind.recyclerView.adapter = artRecyclerAdapter
        bind.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(bind.recyclerView)


        bind.floatingActionButton.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner) {
            artRecyclerAdapter.arts = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}