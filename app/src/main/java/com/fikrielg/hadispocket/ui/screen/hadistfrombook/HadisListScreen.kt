package com.fikrielg.hadispocket.ui.screen.hadistfrombook


import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.fikrielg.hadispocket.HadisPocketApplication
import com.fikrielg.hadispocket.data.factory.viewModelFactory
import com.fikrielg.hadispocket.data.source.local.kotpref.SharedPref
import com.fikrielg.hadispocket.ui.component.ErrorMessage
import com.fikrielg.hadispocket.ui.component.HadisItem
import com.fikrielg.hadispocket.ui.component.ProgressBarComponent
import com.fikrielg.hadispocket.ui.screen.hadistfrombook.HadisListViewModel.*
import com.fikrielg.hadispocket.ui.theme.montserrat
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

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
    val bookName = viewModel.book
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    var fontSizeArab by remember { mutableStateOf(SharedPref.fontArabSize) }
    var fontSizeIndonesia by remember { mutableStateOf(SharedPref.fontIdSize) }

    fun onSliderValueChanged(value: Float, isArab: Boolean) {
        if (isArab) {
            fontSizeArab = value.coerceIn(12f, 30f)
            SharedPref.fontArabSize = fontSizeArab
        } else {
            fontSizeIndonesia = value.coerceIn(12f, 30f)
            SharedPref.fontIdSize = fontSizeArab
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = bookName,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            showBottomSheet = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Setting",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                navigationIcon = {
                                 IconButton(onClick = { navigator.popBackStack()}) {
                                     Icon(
                                         imageVector = Icons.Default.ArrowBack,
                                         contentDescription = "Back",
                                         tint = MaterialTheme.colorScheme.onBackground
                                     )                                 }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
            )
        },
    ) {


        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Arab Font Size")
                        Slider(
                            value = fontSizeArab,
                            onValueChange = { value ->
                                onSliderValueChanged(value, isArab = true)
                            },
                            valueRange = 12f..30f,
                            steps = 19,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Indo Font Size")
                        Slider(
                            value = fontSizeIndonesia,
                            onValueChange = { value ->
                                onSliderValueChanged(value, isArab = false)
                            },
                            valueRange = 12f..30f,
                            steps = 19,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

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
                        onTapCopy = {
                            Toast.makeText(
                                context,
                                "Copied to clipboard",
                                Toast.LENGTH_SHORT
                            ).show()
                            clipboardManager.setText(
                                AnnotatedString(
                                    "${hadisItem!!.arab}\n\n${hadisItem.id}\n$bookName no.${hadisItem.number}"
                                )
                            )
                        },
                        onTapShare = { context.startActivity(shareIntent) },
                        fontArabSize = fontSizeArab,
                        fontIdSize = fontSizeIndonesia
                    )
                }
                when (hadisList.loadState.refresh) {
                    is LoadState.Error -> item {
                        Log.d(
                            "ERR",
                            (hadisList.loadState.refresh as LoadState.Error).error.toString()
                        )
                        ErrorMessage(message = "Error when fetching API", onClick = {viewModel.hadisList})
                    }

                    is LoadState.Loading -> item {
                        ProgressBarComponent()
                    }

                    else -> {}
                }

                when (hadisList.loadState.append) {
                    is LoadState.Error -> item {
                        ErrorMessage(message = "Error to fetch data", onClick = {})
                    }

                    is LoadState.Loading -> item { ProgressBarComponent() }
                    else -> {}
                }
            }
        }
    }


}







