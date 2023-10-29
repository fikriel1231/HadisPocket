package com.fikrielg.hadispocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fikrielg.hadispocket.navigation.AppRouter
import com.fikrielg.hadispocket.ui.theme.HadisPocketTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HadisPocketTheme {
                AppRouter().RouterDelegate(homeViewModel = hiltViewModel(), hadisFromBookViewModel = hiltViewModel())
            }
        }
    }
}

