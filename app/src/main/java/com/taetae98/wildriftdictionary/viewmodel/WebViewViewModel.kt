package com.taetae98.wildriftdictionary.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    val url by lazy { stateHandle.getLiveData("URL", "") }
}