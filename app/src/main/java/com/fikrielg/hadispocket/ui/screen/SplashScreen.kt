package com.fikrielg.hadispocket.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.fikrielg.hadispocket.navigation.AppRouter
import com.fikrielg.hadispocket.navigation.Screen

import kotlinx.coroutines.delay

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(true) {
        delay(2500)
        AppRouter.pushAndReplace(navController = navController, Screen.HomeScreen.route)
    }
     Scaffold() {
        Column(
            modifier = Modifier
                .padding(it).padding(vertical = 100.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
//            Image( modifier = Modifier.size(180.dp),
//                painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Octicons-mark-github.svg/2048px-Octicons-mark-github.svg.png"),
//                contentDescription = "",
//            )
            Text(text = "Hadis Pocket",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
            )
        }
    }
}