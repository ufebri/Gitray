package com.uray.gitray.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import febri.uray.bedboy.core.domain.usecase.AppUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val useCase: AppUseCase) : ViewModel() {

    val favoriteList = useCase.getFavoriteUsers().asLiveData()
}