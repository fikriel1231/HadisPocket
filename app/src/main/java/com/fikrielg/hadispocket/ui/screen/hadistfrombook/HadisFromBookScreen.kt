package com.fikrielg.hadispocket.ui.screen.hadistfrombook


import android.preference.SwitchPreference
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fikrielg.hadispocket.R
import com.fikrielg.hadispocket.core.constant.UiState
import com.fikrielg.hadispocket.core.data.factory.ViewModelFactory
import com.fikrielg.hadispocket.ui.component.DetailHadisItem
import com.fikrielg.hadispocket.ui.component.HadisItem
import com.fikrielg.hadispocket.ui.component.ProgressBarComponent
import com.fikrielg.hadispocket.ui.component.StateInfo
import com.fikrielg.hadispocket.ui.component.StateType
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HadisFromBookScreen(
    hadisFromBookViewModel: HadisFromBookViewModel = viewModel(factory = ViewModelFactory.getInstance()),
    navController: NavHostController = rememberNavController(),
    name: String
) {
    val listOfHadis by hadisFromBookViewModel.listOfHadis.collectAsState(initial = emptyList())
    val listOfHadisState = hadisFromBookViewModel.listOfHadisState.collectAsState().value
    val eventState = hadisFromBookViewModel.eventState.collectAsState().value
    val detailOfBook by hadisFromBookViewModel.detailOfHadis.collectAsState(null)

    var searchQuery by remember { mutableStateOf("") }


    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = listOfHadisState == UiState.Loading)

    LaunchedEffect(true) {
        hadisFromBookViewModel.getDetailOfHadis(name)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hadis ${name}") }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                TextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        hadisFromBookViewModel.setHadisFromBookEvent(HadisFromBookEvent.OnSearch)
                        hadisFromBookViewModel.getDetailOfHadis(name, it)
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            hadisFromBookViewModel.setHadisFromBookEvent(HadisFromBookEvent.OnSearch)
                            hadisFromBookViewModel.getDetailOfHadis(name, searchQuery)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_search_24),
                                contentDescription = ""
                            )
                        }
                    },
                    trailingIcon = {
                        if (eventState == HadisFromBookEvent.OnSearch || searchQuery != "") {
                            IconButton(onClick = {
                                hadisFromBookViewModel.setHadisFromBookEvent(HadisFromBookEvent.OnDefault)
                                searchQuery = ""
                                hadisFromBookViewModel.getDetailOfHadis(name)
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                    placeholder = { Text("Search Hadis") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    singleLine = true
                )
            }
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { hadisFromBookViewModel.getDetailOfHadis(name) }
            ) {
                when (listOfHadisState) {
                    UiState.Loading -> ProgressBarComponent()
                    UiState.Error -> StateInfo(type = StateType.ERROR)
                    UiState.Empty -> StateInfo(type = StateType.EMPTY)
                    UiState.Success -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (eventState != HadisFromBookEvent.OnSearch && searchQuery.isEmpty()) {
                                LazyColumn(
                                    modifier = Modifier.padding(4.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp)
                                ) {
                                    items(listOfHadis) { hadis ->
                                        HadisItem(hadis = hadis, onTap = {

                                        })
                                    }
                                }
                            }else{
                                AnimatedVisibility(visible = eventState == HadisFromBookEvent.OnSearch) {
                                    DetailHadisItem(
                                        detailOfBook?.number ?: 0, detailOfBook?.arab ?: "",
                                        detailOfBook?.id ?: "", onTap = {})
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}