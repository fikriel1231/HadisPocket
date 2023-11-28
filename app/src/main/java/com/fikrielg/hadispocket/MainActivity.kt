package com.fikrielg.hadispocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fikrielg.hadispocket.ui.screen.NavGraphs
import com.fikrielg.hadispocket.ui.theme.HadisPocketTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HadisPocketTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

