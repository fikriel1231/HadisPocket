package com.fikrielg.hadispocket.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fikrielg.hadispocket.HadisPocketApplication
import com.fikrielg.hadispocket.data.factory.viewModelFactory
import com.fikrielg.hadispocket.ui.component.BookItem
import com.fikrielg.hadispocket.ui.component.ErrorMessage
import com.fikrielg.hadispocket.ui.component.ProgressBarComponent

import com.fikrielg.hadispocket.ui.screen.destinations.HadisListScreenDestination
import com.fikrielg.hadispocket.ui.screen.hadistfrombook.HadisListScreenNavArgs
import com.fikrielg.hadispocket.ui.theme.montserrat

import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Destination(start = true)
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



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hadis Pocket", fontFamily = montserrat, fontWeight = FontWeight.SemiBold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {

            when (val state = homeState) {
                is HomeState.Error -> ErrorMessage(
                    message = state.message,
                    onClick = { viewModel.getListOfBooks() })

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
                                    navigator.navigate(
                                        HadisListScreenDestination(
                                            HadisListScreenNavArgs(book.id, book.name)
                                        )
                                    )
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
