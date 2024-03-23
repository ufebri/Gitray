package com.uray.gitray.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import febri.uray.bedboy.core.domain.usecase.AppUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val useCase: AppUseCase) : ViewModel() {

    fun getSettings(key: String) = useCase.getBooleanPreferenceKey(key).asLiveData()

    fun saveKeySettings(key: String, state: Boolean) = useCase.saveBooleanPreferenceKey(key, state)

}