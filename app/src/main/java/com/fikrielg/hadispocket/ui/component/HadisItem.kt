package com.fikrielg.hadispocket.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fikrielg.hadispocket.R


@Composable
fun HadisItem(
    arab:String, number:Int, id:String,
    onTap: (String) -> Unit?,
    onTapShare: () -> Unit,
    onTapCopy: () -> Unit?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable {}
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = number.toString())
            Spacer(modifier = Modifier.width(10.dp))
            Column() {
                Text(text = arab, textAlign = TextAlign.Right)
                Text(text = id)
                Row() {
                    IconButton(onClick = { onTapShare() }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_share_24), contentDescription = "Share Hadis")
                    }
                    IconButton(onClick = { onTapCopy() }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_content_copy_24), contentDescription = "Copy Hadis")
                    }
                }
            }
        }

    }
}