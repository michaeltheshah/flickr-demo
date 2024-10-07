package com.cvshealth.flickrdemo.util.extensions

import com.cvshealth.flickrdemo.util.state.NetworkResult
import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.awaitResult(): NetworkResult<T> {
    return try {
        if (isSuccessful) {
            val body = body()
            if (body != null) {
                NetworkResult.Ok(body, raw()) // body is the value sent back
            } else {
                NetworkResult.Error(NullPointerException("Response body is null"), raw())
            }
        } else {
            NetworkResult.Error(HttpException(this), raw())
        }
    } catch (e: Exception) {
        NetworkResult.Error(e, raw())
    }
}

val <T> Response<T>.value: T?
    get() = try {
        body()
    } catch (e: Exception) {
        null
    }