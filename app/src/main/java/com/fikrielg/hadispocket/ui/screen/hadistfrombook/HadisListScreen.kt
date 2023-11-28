package com.fikrielg.hadispocket.ui.screen.hadistfrombook


import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.fikrielg.hadispocket.HadisPocketApplication
import com.fikrielg.hadispocket.data.factory.viewModelFactory
import com.fikrielg.hadispocket.ui.component.ErrorMessage
import com.fikrielg.hadispocket.ui.component.HadisItem
import com.fikrielg.hadispocket.ui.component.ProgressBarComponent
import com.fikrielg.hadispocket.ui.screen.hadistfrombook.HadisListViewModel.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class HadisListScreenNavArgs(
    val name: String,
    val book: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Destination(navArgsDelegate = HadisListScreenNavArgs::class)
@Composable
fun HadisListScreen(
    viewModel: HadisListViewModel = viewModel(factory = viewModelFactory {
        HadisListViewModel(
            HadisPocketApplication.repository,
            it
        )
    }),
    navigator: DestinationsNavigator
) {

    val hadisList = viewModel.hadisList.collectAsLazyPagingItems()
    val hadisDetailState by viewModel.hadisDetailState.collectAsStateWithLifecycle()
    val bookName = viewModel.book
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current


//    val swipeRefreshState =
//        rememberSwipeRefreshState(isRefreshing = hadisListState == HadisListState.Loading)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hadis ${bookName}") }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(80.dp)
//                    .padding(12.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
//            ) {
//                TextField(
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    value = searchQuery,
//                    onValueChange = {
//                        searchQuery = it
//                        viewModel.setHadisListEvent(HadisListEvent.OnSearch)
//                        viewModel.getDetailOfHadis(name)
//                    },
//                    leadingIcon = {
//                        IconButton(onClick = {
//                            hadisFromBookViewModel.setHadisListEvent(HadisListEvent.OnSearch)
//                            hadisFromBookViewModel.getDetailOfHadis(name, searchQuery)
//                        }) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.baseline_search_24),
//                                contentDescription = ""
//                            )
//                        }
//                    },
//                    trailingIcon = {
//                        if (eventState == HadisListEvent.OnSearch || searchQuery != "") {
//                            IconButton(onClick = {
//                                hadisFromBookViewModel.setHadisListEvent(HadisListEvent.OnDefault)
//                                searchQuery = ""
//                                hadisFromBookViewModel.getDetailOfHadis(name)
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Rounded.Close,
//                                    contentDescription = ""
//                                )
//                            }
//                        }
//                    },
//                    placeholder = { Text("Search Hadis") },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = TextFieldDefaults.textFieldColors(
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                    ),
//                    singleLine = true
//                )
//            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    hadisList.itemCount,
                    key = { hadisList[it]?.number!! },
                    contentType = hadisList.itemContentType { "hadisListPaging" }
                ) { index ->
                    val hadisItem = hadisList[index]
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "${hadisItem!!.arab}\n\n${hadisItem.id}\n$bookName no.${hadisItem.number}"
                        )
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    HadisItem(
                        arab = hadisItem!!.arab,
                        id = hadisItem.id,
                        number = hadisItem.number,
                        onTap = {}, onTapCopy = {
                            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                            clipboardManager.setText(
                                AnnotatedString(
                                    "${hadisItem!!.arab}\n\n${hadisItem.id}\n$bookName no.${hadisItem.number}"
                                )
                            )
                        }, onTapShare = { context.startActivity(shareIntent) })
                }
                when (hadisList.loadState.refresh) {
                    is LoadState.Error -> item {
                        Log.d(
                            "ERR",
                            (hadisList.loadState.refresh as LoadState.Error).error.toString()
                        )
                        ErrorMessage(message = "Error when fetching API")
                    }

                    is LoadState.Loading -> item {
                        ProgressBarComponent()
                    }

                    else -> {}
                }

                when (hadisList.loadState.append) {
                    is LoadState.Error -> item {
                        ErrorMessage(message = "Error to fetch data")
                    }

                    is LoadState.Loading -> item { ProgressBarComponent() }
                    else -> {}
                }
            }

//            SwipeRefresh(
//                state = swipeRefreshState,
//                onRefresh = { hadisFromBookViewModel.getDetailOfHadis(name) }
//            ) {
//                when (listOfHadisState) {
//                    UiState.Loading -> ProgressBarComponent()
//                    UiState.Error -> StateInfo(type = StateType.ERROR)
//                    UiState.Empty -> StateInfo(type = StateType.EMPTY)
//                    UiState.Success -> {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            if (eventState != HadisListEvent.OnSearch && searchQuery.isEmpty()) {
//                                LazyColumn(
//                                    modifier = Modifier.padding(4.dp),
//                                    contentPadding = PaddingValues(horizontal = 10.dp)
//                                ) {
//                                    items(listOfHadis) { hadis ->
//                                        HadisItem(hadis = hadis, onTap = {
//
//                                        })
//                                    }
//                                }
//                            }else{
//                                AnimatedVisibility(visible = eventState == HadisListEvent.OnSearch) {
//                                    DetailHadisItem(
//                                        detailOfBook?.number ?: 0, detailOfBook?.arab ?: "",
//                                        detailOfBook?.id ?: "", onTap = {})
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
    }


}





