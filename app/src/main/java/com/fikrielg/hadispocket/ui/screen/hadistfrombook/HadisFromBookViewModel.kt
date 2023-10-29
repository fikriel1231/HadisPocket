package com.fikrielg.hadispocket.ui.screen.hadistfrombook


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrielg.hadispocket.core.constant.UiState
import com.fikrielg.hadispocket.core.data.repository.MainRepository
import com.fikrielg.hadispocket.core.model.Contents
import com.fikrielg.hadispocket.core.model.Hadith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

enum class HadisFromBookEvent { OnSearch, OnDefault }

@HiltViewModel
class HadisFromBookViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    private val _listOfHadis = MutableStateFlow<List<Hadith>>(emptyList())
    val listOfHadis: StateFlow<List<Hadith>>
        get() = _listOfHadis

    private val _listOfHadisState = MutableStateFlow(UiState.Empty)
    val listOfHadisState: StateFlow<UiState>
        get() = _listOfHadisState

    private val _detailOfHadis = MutableStateFlow<Contents?>(null)
    val detailOfHadis: StateFlow<Contents?>
        get() = _detailOfHadis


    private val _eventState = MutableStateFlow(HadisFromBookEvent.OnDefault)
    val eventState: StateFlow<HadisFromBookEvent>
        get() = _eventState


    fun getDetailOfHadis(name: String, number: String? = "") {
        when (_eventState.value) {
            HadisFromBookEvent.OnSearch -> {
                viewModelScope.launch {
                    if (number == "") return@launch
                    try {
                        _setListOfHadisState(UiState.Loading)
                        delay(200)
                        val response = mainRepository.getDetailHadis(
                            name, number?.toInt() ?: 1
                        )
                        _detailOfHadis.value = response.data.contents
                        _setListOfHadisState(UiState.Success)
                    } catch (e: Exception) {
                        Log.d("Detail Hadis Exception", "${e.message}")
                        _setListOfHadisState(UiState.Error)
                    }
                }
            }

            HadisFromBookEvent.OnDefault -> {
                viewModelScope.launch {
                    try {
                        _setListOfHadisState(UiState.Loading)
                        delay(1200)
                        val response = mainRepository.getListHadisFromBook(
                            name
                        )
                        _listOfHadis.value = response.data.hadiths
                        _setListOfHadisState(UiState.Success)
                    } catch (e: Exception) {
                        Log.d("List Hadis Exception", "${e.message}")
                        _setListOfHadisState(UiState.Error)
                    }
                }
            }
        }
    }

    private fun _setListOfHadisState(value: UiState) {
        _listOfHadisState.value = value
    }

    fun setHadisFromBookEvent(value: HadisFromBookEvent) {
        _eventState.value = value
    }
}