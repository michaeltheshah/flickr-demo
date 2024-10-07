package com.cvshealth.flickrdemo.data.repository.search

import com.cvshealth.flickrdemo.data.model.search.PublicPhotosSearchResponse
import com.cvshealth.flickrdemo.util.extensions.awaitResult
import com.cvshealth.flickrdemo.util.state.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class FlickrRepository @Inject constructor(private val service: FlickrService) {
    open suspend fun getSearchResults(input: String): NetworkResult<PublicPhotosSearchResponse> {
        return withContext(Dispatchers.IO) {
            service.getSearchResults(input).awaitResult()
        }
    }
}