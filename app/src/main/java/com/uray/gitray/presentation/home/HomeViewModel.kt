package com.uray.gitray.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import febri.uray.bedboy.core.domain.usecase.AppUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: AppUseCase) : ViewModel() {

    val lastVisitList = useCase.getLastVisit().asLiveData()

    fun getSearchUser(keyword: String) =
        useCase.getSearchListUser(keyword).asLiveData().cachedIn(viewModelScope)
}