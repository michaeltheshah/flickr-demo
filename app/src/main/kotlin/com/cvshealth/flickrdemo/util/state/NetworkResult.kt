package com.cvshealth.flickrdemo.util.state

import okhttp3.Response

sealed interface NetworkResult<out R> {
    data class Ok<out T>(val value: T, val response: Response? = null): NetworkResult<T>
    data class Error(val exception: Exception, val response: Response? = null): NetworkResult<Nothing>
}