package com.cvshealth.flickrdemo.ui.screens

import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cvshealth.flickrdemo.data.model.search.SearchResult
import com.cvshealth.flickrdemo.ui.viewmodel.FlickrViewModel
import com.cvshealth.flickrdemo.util.extensions.formatted
import kotlinx.datetime.format

@Composable
fun FlickrResultDetailsScreen(
    viewModel: FlickrViewModel,
    link: String,
    modifier: Modifier = Modifier,
) {
    val currentSearchResult = viewModel.getItemById(link)

    if (currentSearchResult != null) {
        Column(modifier) {
            FlickrDetailBody(searchResult = currentSearchResult)
        }
    }
}

@Composable
fun FlickrDetailBody(
    modifier: Modifier = Modifier,
    searchResult: SearchResult,
) {
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = searchResult.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.Center),
                model = searchResult.media.mediaLink,
                contentDescription = "Flickr photo result"
            )
        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = searchResult.title,
            fontSize = 24.sp
        )

        AndroidView(
            modifier = modifier,
            factory = { context -> TextView(context) },
            update = {
                it.text =
                    HtmlCompat.fromHtml(searchResult.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
            }
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = searchResult.author,
            fontSize = 18.sp
        )

        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = searchResult.published.formatted(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}