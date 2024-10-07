package com.cvshealth.flickrdemo.models

import com.cvshealth.flickrdemo.data.model.search.PublicPhotosSearchResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PublicPhotosSearchResponseTest {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        isLenient = true
        explicitNulls = false
        coerceInputValues = true
    }
    private var searchResponse: PublicPhotosSearchResponse? = null

    @OptIn(ExperimentalSerializationApi::class)
    @Before
    fun setUp() {
        searchResponse = try {
            val stream = javaClass.getResourceAsStream("/flickr_photos_search_response.json") ?: return
            json.decodeFromStream<PublicPhotosSearchResponse>(stream)
        } catch (e: Exception) {
            null
        }
    }

    @Test
    fun `test should have search results`() {
        assert(searchResponse?.searchResults?.size == 20)
    }
}