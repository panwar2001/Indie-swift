package com.panwar2001.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GradientButton(
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier= Modifier,
    content:@Composable ()-> Unit
) {
    Button (
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = onClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}
/*
        Box(
            modifier = Modifier.fillMaxSize() // Fill the available space
        ) {
            GradientButton(
                onClick = onRefresh,
                modifier = Modifier.align(Alignment.Center),
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
                    Text(text = "Reload", color = Color.White, fontSize = 16.sp)
                }
            }
        }
 */