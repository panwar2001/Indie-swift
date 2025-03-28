package com.panwar2001.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panwar2001.myapplication.R
import com.panwar2001.myapplication.ui.theme.color1
import com.panwar2001.myapplication.ui.theme.color2

@Composable
fun NavigationDrawerContent(loadMetaData:()->Unit,
                            isRefreshing: Boolean,
                            online: Boolean){
    // Content of the drawer (side navigation menu)
    ModalDrawerSheet {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                Image( painter = painterResource(R.drawable.round_icon), contentDescription = null)
                Text(
                    "Indie App",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(Modifier.height(12.dp))
            StatusRow(online)
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,){
                GradientButton(
                    onClick = { loadMetaData() },
                    gradient = Brush.horizontalGradient(colors = listOf(color1, color2))
                ) {
                    Row {
                        if (isRefreshing) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.then(Modifier.size(16.dp)),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reload"
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Fetch Latest Medicine List",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun StatusRow(online: Boolean) {
    Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Green dot circle (as an icon)
        Box(
            modifier = Modifier
                .size(16.dp) // Circle size
                .clip(CircleShape) // Making it a circle
                .background(if(!online) Color.Red else Color.Green) // Green color fill
        )

        // Text "Online" or "Offline"
        Text(
            text = if (online) "Online" else "Offline",
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
