package com.astro.test.irwan.utils

sealed class StateEvent<T> {
    class Loading<T>: StateEvent<T>()
    data class Error<T>(val exception: String): StateEvent<T>()
    data class Success<T>(val data: T): StateEvent<T>()}
