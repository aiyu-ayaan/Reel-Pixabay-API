package com.atech.reel.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.atech.reel.R
import com.atech.reel.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerViewVideos.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = HomeAdapter().also {
                    homeAdapter = it
                }
                setHasFixedSize(true)
            }
            homeAdapter.addLoadStateListener { loadState ->
                if (loadState.refresh is androidx.paging.LoadState.Loading) progressCircular.visibility =
                    View.VISIBLE
                else {
                    progressCircular.visibility = View.GONE
                    val error = when {
                        loadState.prepend is androidx.paging.LoadState.Error -> loadState.prepend as androidx.paging.LoadState.Error
                        loadState.append is androidx.paging.LoadState.Error -> loadState.append as androidx.paging.LoadState.Error
                        loadState.refresh is androidx.paging.LoadState.Error -> loadState.refresh as androidx.paging.LoadState.Error
                        else -> null
                    }
                    error?.let {
                        Toast.makeText(
                            requireContext(), "${error.error.message}", Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "onViewCreated: ${error.error.message}")
                    }
                }
            }
        }
        loadData()
    }

    private fun loadData() = lifecycleScope.launchWhenStarted {
        viewModel.videos.collect(homeAdapter::submitData)
    }


}