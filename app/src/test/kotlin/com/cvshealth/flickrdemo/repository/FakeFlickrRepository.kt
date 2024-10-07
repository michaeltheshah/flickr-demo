package com.cvshealth.flickrdemo.repository

import com.cvshealth.flickrdemo.data.model.search.PublicPhotosSearchResponse
import com.cvshealth.flickrdemo.data.model.search.SearchResult
import com.cvshealth.flickrdemo.data.repository.search.FlickrRepository
import com.cvshealth.flickrdemo.data.repository.search.FlickrService
import com.cvshealth.flickrdemo.util.state.NetworkResult
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

class FakeFlickrRepository @Inject constructor(service: FlickrService) : FlickrRepository(service) {
    var shouldReturnError = false
    private val fakeResponse = PublicPhotosSearchResponse(
        title = "Recent Uploads tagged space",
        link = "https://www.flickr.com/photos/tags/space/",
        description = "",
        searchResults = listOf(
            SearchResult(
                title = "#1557 The Swimmer",
                link = "https://www.flickr.com/photos/55610845@N05/54041989042/",
                description = "Story about a fishing tournament in space.",
            ),
            SearchResult(
                title = "Machine or ???",
                link = "https://www.flickr.com/photos/silentoneproductions/54043139759/",
                description = "Rolling out the 'stars' above your head.",
            )
        )
    )

    override suspend fun getSearchResults(input: String): NetworkResult<PublicPhotosSearchResponse> {
        return if (shouldReturnError) {
            NetworkResult.Error(IllegalStateException(""))
        } else {
            NetworkResult.Ok(fakeResponse)
        }
    }
}
