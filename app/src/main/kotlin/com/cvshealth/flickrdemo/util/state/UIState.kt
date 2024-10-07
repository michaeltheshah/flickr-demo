package com.cvshealth.flickrdemo.util.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
sealed class UIState<out R: Any?>: Parcelable {
    data class Idle(val isLoading: Boolean = false): UIState<Nothing>(), Parcelable
    data class Success<T>(val value: @RawValue T): UIState<T>()
    data class Error(val exception: Throwable): UIState<Nothing>()
}