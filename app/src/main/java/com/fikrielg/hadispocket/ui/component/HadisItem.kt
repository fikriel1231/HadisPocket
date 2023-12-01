package com.fikrielg.hadispocket.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrielg.hadispocket.R
import com.fikrielg.hadispocket.ui.theme.montserrat
import com.fikrielg.hadispocket.ui.theme.uthmanic


@Composable
fun HadisItem(
    arab: String, number: Int, id: String,
    onTapShare: () -> Unit,
    onTapCopy: () -> Unit,
    fontArabSize: Float,
    fontIdSize: Float
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = number.toString(), color = Color.Black
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column() {
                Text(
                    text = arab,
                    textAlign = TextAlign.Right,
                    fontFamily = uthmanic,
                    fontSize = fontArabSize.sp,
                    lineHeight = (fontArabSize * 1.5).sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = id,
                    fontFamily = montserrat,
                    fontSize = fontIdSize.sp,
                    lineHeight = (fontIdSize * 1.2).sp,
                    color = Color.Black
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = { onTapShare() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_share_24),
                            contentDescription = "Share Hadis",
                                    tint = Color.Gray

                        )
                    }
                    IconButton(onClick = { onTapCopy() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_content_copy_24),
                            contentDescription = "Copy Hadis",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }

    }
}