package com.panwar2001.myapplication.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.panwar2001.myapplication.R
import com.panwar2001.myapplication.ui.theme.errorColor
import com.panwar2001.myapplication.ui.theme.greenLimeLight
import kotlinx.coroutines.delay

@Composable
fun OfflineNetworkStatus() {
    Box(
        modifier = Modifier
            .background(errorColor)
            .animateContentSize()
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.internet_off),
                contentDescription = "internet off",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "No Internet Connection!", color = Color.White,
                fontWeight = Bold
            )
        }
    }
}

@Composable
fun OnlineNetworkStatus() {
    var showOfflineStatus by remember { mutableStateOf(true) }
    // Launch the effect to hide the status after 1 second
    LaunchedEffect(Unit) {
        delay(1000) // Delay for 1 second
        showOfflineStatus = false
    }
    if (showOfflineStatus) {
        Box(
            modifier = Modifier
                .background(greenLimeLight)
                .animateContentSize()
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.internet_on),
                    contentDescription = "internet on",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Back Online!", color = Color.White,
                    fontWeight = Bold
                )
            }
        }
    }
}
