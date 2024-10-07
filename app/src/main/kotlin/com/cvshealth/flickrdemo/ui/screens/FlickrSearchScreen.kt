package com.cvshealth.flickrdemo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cvshealth.flickrdemo.data.model.search.Media
import com.cvshealth.flickrdemo.data.model.search.SearchResult
import com.cvshealth.flickrdemo.ui.viewmodel.FlickrViewModel

@Composable
fun FlickrSearchScreen(
    viewModel: FlickrViewModel,
    contentPadding: PaddingValues,
    onNavigateToDetail: (String) -> Unit
) {
    val isLoading = viewModel.isLoading
    val searchResults = viewModel.searchResultsFlow.collectAsStateWithLifecycle(initialValue = null).value

    Column(
        Modifier
            .fillMaxSize()
            .padding(contentPadding)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.input,
            onValueChange = viewModel::updateInput
        )

        if (isLoading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }

        if (searchResults != null) {
            FlickrSearchBody(searchResults.searchResults, onNavigateToDetail)
        }
    }
}

@Composable
fun FlickrSearchBody(
    results: List<SearchResult>,
    onNavigateToDetail: (String) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(results) { result ->
            FlickrResultItem(
                result,
                onNavigateToDetail
            )
        }
    }
}

@Composable
fun FlickrResultItem(
    result: SearchResult,
    onNavigateToDetail: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetail(result.link) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = result.media.mediaLink,
            contentDescription = result.title,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun FlickerResultItem_Preview() {
    val searchResults = listOf(
        SearchResult(media = Media("https://live.staticflickr.com/31337/54043139759_eeb48cd109_m.jpg")),
        SearchResult(media = Media("https://live.staticflickr.com/31337/54042746836_23c4d6a5b4_m.jpg")),
        SearchResult(media = Media("https://live.staticflickr.com/65535/54041843077_575191aab0_m.jpg")),
        SearchResult(media = Media("https://live.staticflickr.com/65535/54042159141_a8c9d6df1d_m.jpg"))
    )

    FlickrSearchBody(searchResults) {}
}