package com.astro.test.irwan.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.astro.test.irwan.core.domain.usecase.AstroUseCase

class MainViewModel(private val astroUseCase: AstroUseCase) : ViewModel() {

    fun users(username: String, page: Int, perPage: Int) =
        astroUseCase.users(username, page, perPage).cachedIn(viewModelScope).asLiveData()
}