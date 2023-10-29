package com.fikrielg.hadispocket.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fikrielg.hadispocket.core.model.Data
import com.fikrielg.hadispocket.navigation.AppRouter
import com.fikrielg.hadispocket.navigation.Screen

@Composable
fun BookItem(
    book: Data,
    navHostController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
            .clickable {AppRouter.push(navHostController, Screen.HadisFromBookScreen.createRoute(book.id))}
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = book.name)
            Text(text = "Tersedia ${book.available} hadis")
        }

    }
}