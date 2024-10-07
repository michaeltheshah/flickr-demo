package com.cvshealth.flickrdemo.data.repository.search

import com.cvshealth.flickrdemo.data.model.search.PublicPhotosSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    @GET("/services/feeds/photos_public.gne")
    suspend fun getSearchResults(
        @Query("tags") input: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJSONCallback: Int = 1
    ): Response<PublicPhotosSearchResponse>
}