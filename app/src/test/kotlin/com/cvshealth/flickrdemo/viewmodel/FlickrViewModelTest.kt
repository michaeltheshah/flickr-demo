package com.cvshealth.flickrdemo.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.cvshealth.flickrdemo.data.model.search.PublicPhotosSearchResponse
import com.cvshealth.flickrdemo.data.model.search.SearchResult
import com.cvshealth.flickrdemo.data.repository.search.FlickrRepository
import com.cvshealth.flickrdemo.data.repository.search.FlickrService
import com.cvshealth.flickrdemo.repository.FakeFlickrRepository
import com.cvshealth.flickrdemo.ui.viewmodel.FlickrViewModel
import com.cvshealth.flickrdemo.util.state.NetworkResult
import com.cvshealth.flickrdemo.util.state.UIState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class FlickrViewModelTest {

    private val flickrService = mockk<FlickrService>()
    private lateinit var viewModel: FlickrViewModel
    private lateinit var repository: FakeFlickrRepository
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)
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
    fun setup() {
        repository = FakeFlickrRepository(flickrService)

        searchResponse = try {
            val stream = javaClass.getResourceAsStream("/flickr_photos_search_response.json") ?: return
            json.decodeFromStream<PublicPhotosSearchResponse>(stream)
        } catch (e: Exception) {
            null
        }

        viewModel = FlickrViewModel(
            savedStateHandle = SavedStateHandle(),
            repository = repository
        )
    }

    @Test
    fun `initial state is correct`() {
        assertEquals("", viewModel.input)
        assertEquals(emptyList<SearchResult>(), viewModel.searchResults)
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(UIState.Idle(), viewModel.state)
    }

    @Test
    fun `updateInput should update the input value`() {
        val newInput = "New Search"
        viewModel.updateInput(newInput)
        assertEquals(newInput, viewModel.input)
    }

    @Test
    fun `searchResultsFlow should emit null when input is blank`() = testScope.runTest {
        viewModel.updateInput("")
        viewModel.searchResultsFlow.test {
            assertEquals(null, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getItemById should return correct item`() {
        val searchResult1 = SearchResult(link = "https://www.flickr.com/photos/55610845@N05/54041989042/", title = "#1557 The Swimmer")
        val searchResult2 = SearchResult(link = "https://www.flickr.com/photos/silentoneproductions/54043139759/", title = "Machine or ???")
        viewModel.searchResults = listOf(searchResult1, searchResult2)

        val result = viewModel.getItemById("https://www.flickr.com/photos/55610845@N05/54041989042/")
        assertEquals(searchResult1, result)

        val resultNotFound = viewModel.getItemById("https://example.com/1")
        assertEquals(null, resultNotFound)
    }
}
