package com.fikrielg.hadispocket.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fikrielg.hadispocket.HadisPocketApplication
import com.fikrielg.hadispocket.core.constant.UiState
import com.fikrielg.hadispocket.data.factory.viewModelFactory
import com.fikrielg.hadispocket.ui.component.BookItem
import com.fikrielg.hadispocket.ui.component.ErrorMessage
import com.fikrielg.hadispocket.ui.component.ProgressBarComponent
import com.fikrielg.hadispocket.ui.component.StateInfo
import com.fikrielg.hadispocket.ui.component.StateType
import com.fikrielg.hadispocket.ui.screen.destinations.HadisListScreenDestination
import com.fikrielg.hadispocket.ui.screen.hadistfrombook.HadisListScreenNavArgs
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator



@OptIn(ExperimentalMaterial3Api::class)
@Destination()
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = viewModel(
        factory = viewModelFactory {
            HomeViewModel(HadisPocketApplication.repository)
        }
    )
) {

    viewModel.getListOfBooks()
    val homeState by viewModel.getAllBookState.collectAsStateWithLifecycle()

    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = homeState == HomeState.Loading)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hadis Pocket") }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { viewModel.getListOfBooks() }
            ) {
                when(val state = homeState){
                    is HomeState.Error -> ErrorMessage(message = state.message)
                    is HomeState.Loading -> ProgressBarComponent()
                    is HomeState.Success -> {
                        LazyColumn(
                            modifier = Modifier.padding(4.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {
                            items(state.list.data) { book ->
                                BookItem(
                                    book = book,
                                    onClick = {
                                        navigator.navigate(HadisListScreenDestination(HadisListScreenNavArgs(book.id, book.name)))
                                    }
                                )
                            }
                        }
                    }
                    null -> {

                    }
                }
            }
        }
    }
}