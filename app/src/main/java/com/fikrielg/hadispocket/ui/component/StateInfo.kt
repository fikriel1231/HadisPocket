package com.fikrielg.hadispocket.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fikrielg.hadispocket.R

enum class StateType {
    ERROR, LOADING, EMPTY
}

class Info(
    val title:String,
    val icon: Int
)

@Composable
fun StateInfo(type: StateType, callback: () -> Unit? = {}) {
    val info : Info = when(type){
        StateType.ERROR -> Info("Unknown Error\nClick Here to Retry", R.drawable.baseline_error_24)
        StateType.LOADING -> TODO()
        StateType.EMPTY -> Info("Oh no..\nWhat you are looking for is empty", R.drawable.baseline_question_mark_24)
    }
    val onTap: () -> Unit = {
        if (type == StateType.ERROR) {
            callback()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(painter = painterResource(id = info.icon), contentDescription = "message", Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Text(info.title,
            modifier = Modifier.clickable { onTap() },
            textAlign = TextAlign.Center
        )
    }
}

