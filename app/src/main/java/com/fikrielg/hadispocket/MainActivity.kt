package com.fikrielg.hadispocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fikrielg.hadispocket.core.data.factory.ViewModelFactory
import com.fikrielg.hadispocket.navigation.AppRouter
import com.fikrielg.hadispocket.ui.theme.HadisPocketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HadisPocketTheme {
                AppRouter().RouterDelegate(homeViewModel = viewModel(factory = ViewModelFactory.getInstance()), hadisFromBookViewModel =  viewModel(factory = ViewModelFactory.getInstance()))
            }
        }
    }
}

