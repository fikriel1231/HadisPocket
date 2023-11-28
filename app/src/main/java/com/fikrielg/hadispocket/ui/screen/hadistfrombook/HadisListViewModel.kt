package com.fikrielg.hadispocket.ui.screen.hadistfrombook


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fikrielg.hadispocket.data.repository.HadisRepository
import com.fikrielg.hadispocket.data.source.remote.model.DetailHadisResponse
import com.fikrielg.hadispocket.ui.screen.navArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class HadisListEvent { OnSearch, OnDefault }

class HadisListViewModel(
    private val repository: HadisRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _hadisDetailState = MutableStateFlow<HadisDetailState>(HadisDetailState.Loading)
    val hadisDetailState = _hadisDetailState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HadisDetailState.Loading)

    private val _eventState = MutableStateFlow(HadisListEvent.OnDefault)
    val eventState: StateFlow<HadisListEvent>
        get() = _eventState

    val name = savedStateHandle.navArgs<HadisListScreenNavArgs>().name
    val book = savedStateHandle.navArgs<HadisListScreenNavArgs>().book

    val hadisList = repository.getListHadis(
        name
    ).cachedIn(viewModelScope)

    init {
        Log.d("NAME", name)
    }

    suspend fun getDetailOfHadis(number: String? = "") {
        when (_eventState.value) {
            HadisListEvent.OnSearch -> {
                viewModelScope.launch {
                    _hadisDetailState.emit(HadisDetailState.Loading)
                    if (number == "" || number.isNullOrEmpty()) return@launch
                    try {
                        _hadisDetailState.emit(
                            HadisDetailState.Success(
                                repository.getDetailHadis(
                                    name, number.toInt()
                                )
                            )
                        )
                    } catch (e: Exception) {
                        Log.d("Detail Hadis Exception", "${e.message}")
                        _hadisDetailState.emit(HadisDetailState.Error(e))
                    }
                }
            }

            HadisListEvent.OnDefault -> {

            }
        }
    }

    fun setHadisListEvent(value: HadisListEvent) {
        _eventState.value = value
    }


    sealed class HadisDetailState {

        object Loading : HadisDetailState()

        data class Error(val e: Exception) : HadisDetailState()

        data class Success(val hadis: DetailHadisResponse) : HadisDetailState()

    }
}