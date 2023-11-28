package com.fikrielg.hadispocket.data.factory

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <VM:ViewModel> viewModelFactory(initializer:(SavedStateHandle) -> VM): AbstractSavedStateViewModelFactory{
    @Suppress("UNCHECKED_CAST")
    return object : AbstractSavedStateViewModelFactory(){
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return initializer(handle) as T
        }
    }
}

