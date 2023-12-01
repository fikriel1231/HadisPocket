package com.fikrielg.hadispocket.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrielg.hadispocket.data.repository.HadisRepository
import com.fikrielg.hadispocket.data.source.remote.model.AllBooksResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: HadisRepository,
) : ViewModel() {

    private var _getAllBookState = MutableStateFlow<HomeState?>(HomeState.Loading)
    var getAllBookState = _getAllBookState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HomeState.Loading
    )

    fun getListOfBooks() {
        viewModelScope.launch() {
            _getAllBookState.emit(HomeState.Loading)
            try {
               _getAllBookState.emit(HomeState.Success(repository.getAllBooks()))
            } catch (e: Exception) {
                Log.d("Home Exception", "${e.message}")
                _getAllBookState.emit(HomeState.Error(e.message.toString()))
            }
        }
    }

}


sealed class HomeState {
    object Loading : HomeState()
    data class Error(val message:String) : HomeState()
    data class Success(val list: AllBooksResponse) : HomeState()
}