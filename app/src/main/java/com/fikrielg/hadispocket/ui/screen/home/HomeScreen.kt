package com.fikrielg.hadispocket.ui.screen.home

import android.preference.SwitchPreference
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fikrielg.hadispocket.core.constant.UiState
import com.fikrielg.hadispocket.ui.component.BookItem
import com.fikrielg.hadispocket.ui.component.ProgressBarComponent
import com.fikrielg.hadispocket.ui.component.StateInfo
import com.fikrielg.hadispocket.ui.component.StateType
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val listOfBooks by homeViewModel.listOfBooks.collectAsState(initial = emptyList())
    val listOfBooksState = homeViewModel.listOfBooksState.collectAsState().value

    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = listOfBooksState == UiState.Loading)

    LaunchedEffect(true) {
        homeViewModel.getListOfBooks()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hadis Pocket")}
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {homeViewModel.getListOfBooks()}
            ){
                when(listOfBooksState){
                    UiState.Loading -> ProgressBarComponent()
                    UiState.Error -> StateInfo(type = StateType.ERROR)
                    UiState.Empty -> StateInfo(type = StateType.EMPTY)
                    UiState.Success -> {
                        LazyColumn(modifier = Modifier.padding(4.dp), contentPadding = PaddingValues(horizontal = 10.dp)){
                            items(listOfBooks){book ->
                                BookItem(book = book, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}