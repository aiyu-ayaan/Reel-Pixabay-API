package com.atech.reel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.atech.reel.data.PixaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PixaRepository
) : ViewModel() {

    val videos = repository.getVideos(DEF_QUERY)
        .cachedIn(viewModelScope)

    companion object {
        const val DEF_QUERY = "nature"
    }
}