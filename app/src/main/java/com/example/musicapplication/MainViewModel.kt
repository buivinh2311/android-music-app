package com.example.musicapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.usecase.CleanUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cleanUpUseCase: CleanUpUseCase
) : ViewModel() {
    init {
        Log.d("VIEWMODEL", "haha")
        viewModelScope.launch {
            cleanUpUseCase()
        }
    }
}