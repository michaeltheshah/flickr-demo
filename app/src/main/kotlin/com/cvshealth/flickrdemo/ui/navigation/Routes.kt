package com.cvshealth.flickrdemo.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class FlickrResultDetail(val link: String)
