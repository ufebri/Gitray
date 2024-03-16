package com.uray.gitray.presentation.detailuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import febri.uray.bedboy.core.domain.usecase.AppUseCase
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val useCase: AppUseCase
) : ViewModel() {

    fun getDetailUser(username: String) = useCase.getUser(username).asLiveData()

    fun getFollowersData(username: String) = useCase.getFollowers(username).asLiveData()

    fun getFollowingData(username: String) = useCase.getFollowing(username).asLiveData()
}