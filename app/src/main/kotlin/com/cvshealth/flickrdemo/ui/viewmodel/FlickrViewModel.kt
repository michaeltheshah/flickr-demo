package com.cvshealth.flickrdemo.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.cvshealth.flickrdemo.data.model.search.PublicPhotosSearchResponse
import com.cvshealth.flickrdemo.data.model.search.SearchResult
import com.cvshealth.flickrdemo.data.repository.search.FlickrRepository
import com.cvshealth.flickrdemo.util.state.NetworkResult
import com.cvshealth.flickrdemo.util.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val repository: FlickrRepository
): ViewModel() {
    data class State(val searchResults: List<SearchResult> = listOf())

    @OptIn(SavedStateHandleSaveableApi::class)
    var input by savedStateHandle.saveable { mutableStateOf("") }
        internal set

    @OptIn(SavedStateHandleSaveableApi::class)
    var searchResults: List<SearchResult> by savedStateHandle.saveable { mutableStateOf(emptyList()) }
        internal set

    @OptIn(SavedStateHandleSaveableApi::class)
    var isLoading by savedStateHandle.saveable { mutableStateOf(false) }
        internal set

    fun getItemById(link: String): SearchResult? {
        return searchResults.find { it.link == link }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResultsFlow: SharedFlow<PublicPhotosSearchResponse?> =
        snapshotFlow { input }
            .debounce(300)
            .mapLatest { input ->
                isLoading = true
                if (input.isBlank()) {
                    return@mapLatest null
                }

                when (val result = repository.getSearchResults(input)) {
                    is NetworkResult.Ok -> {
                        result.value
                    }
                    is NetworkResult.Error -> null
                }
            }
            .onEach { results ->
                isLoading = false
                searchResults = results?.searchResults ?: listOf()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = null
            )

    @OptIn(SavedStateHandleSaveableApi::class)
    var state: UIState<State> by savedStateHandle.saveable { mutableStateOf(UIState.Idle()) }
        internal set

    fun updateInput(input: String) {
        this.input = input
    }
}