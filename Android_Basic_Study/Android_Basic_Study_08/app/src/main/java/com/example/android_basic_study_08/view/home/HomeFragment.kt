package com.example.android_basic_study_08.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_08.BookmarkAdapter
import com.example.android_basic_study_08.NewImageAdapter
import com.example.android_basic_study_08.databinding.FragmentHomeBinding
import com.example.android_basic_study_08.utils.UiState

class HomeFragment: Fragment(){
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var bookmarkAdapter : BookmarkAdapter
    private lateinit var newImageAdapter: NewImageAdapter
    private var isLoading = false
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        setupScrollListener()
        bookmarkAdapter = BookmarkAdapter()
        binding.recyclerviewBookmark.adapter = bookmarkAdapter
        binding.recyclerviewBookmark.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        newImageAdapter = NewImageAdapter()
        binding.recyclerviewNewImage.adapter = newImageAdapter
        binding.recyclerviewNewImage.layoutManager = GridLayoutManager(requireContext(), 2)

        homeViewModel.getPhotos(currentPage)
    }

    private fun setupScrollListener() {
        binding.recyclerviewNewImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("dd","dd")
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                if (!isLoading) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == newImageAdapter.itemCount - 1
                    ) {
                        Log.d("dd","if문 안")
                        homeViewModel.getPhotos(currentPage)
                        currentPage++
                        isLoading = true
                    }

                }
            }
        })
    }


    private fun observer(){
        homeViewModel.newState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Failure -> {
                    Log.d("TAG","fail")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
                is UiState.Loading -> {
                    Log.d("TAG","loading")
                }
                is UiState.Success -> {
                    Log.d("TAG","success")
                    newImageAdapter.addItem(it.data)
                    isLoading = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}