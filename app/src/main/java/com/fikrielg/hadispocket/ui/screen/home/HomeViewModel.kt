package com.fikrielg.hadispocket.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrielg.hadispocket.core.constant.UiState
import com.fikrielg.hadispocket.core.data.repository.MainRepository
import com.fikrielg.hadispocket.core.model.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel(){
    private  val _listOfBooks = MutableStateFlow<List<Data>>(emptyList())
    val listOfBooks: StateFlow<List<Data>>
        get() = _listOfBooks

    private val _listOfBooksState = MutableStateFlow(UiState.Empty)
    val listOfBooksState: StateFlow<UiState>
        get() = _listOfBooksState

    fun getListOfBooks(){
        viewModelScope.launch() {
            try {
                _setListOfBooksState(UiState.Loading)
                delay(1200)
                val response = mainRepository.getAllBooks()
                _listOfBooks.value = response.data
                _setListOfBooksState(UiState.Success)
            }catch (e: Exception){
                Log.d("Home Exception", "${e.message}")
                _setListOfBooksState(UiState.Error)
            }
        }
    }

    private fun _setListOfBooksState(value: UiState){
        _listOfBooksState.value = value
    }
}