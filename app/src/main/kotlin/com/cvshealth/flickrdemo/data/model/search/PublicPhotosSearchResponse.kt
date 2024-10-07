package com.cvshealth.flickrdemo.data.model.search

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublicPhotosSearchResponse(
    @SerialName("description")
    val description: String = "",
    @SerialName("generator")
    val generator: String = "",
    @SerialName("items")
    val searchResults: List<SearchResult> = listOf(),
    @SerialName("link")
    val link: String = "",
    @SerialName("modified")
    val modified: Instant = Clock.System.now(),
    @SerialName("title")
    val title: String = ""
)

@Serializable
data class SearchResult(
    @SerialName("author")
    val author: String = "",
    @SerialName("author_id")
    val authorID: String = "",
    @SerialName("date_taken")
    val dateTaken: Instant = Clock.System.now(),
    @SerialName("description")
    val description: String = "",
    @SerialName("link")
    val link: String = "",
    @SerialName("media")
    val media: Media = Media(),
    @SerialName("published")
    val published: Instant = Clock.System.now(),
    @SerialName("tags")
    val tags: String = "",
    @SerialName("title")
    val title: String = ""
)

@Serializable
data class Media(
    @SerialName("m")
    val mediaLink: String = ""
)