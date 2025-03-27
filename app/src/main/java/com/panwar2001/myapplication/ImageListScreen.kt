package com.panwar2001.myapplication

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.panwar2001.myapplication.ui.theme.color1
import com.panwar2001.myapplication.ui.theme.color2
import java.nio.file.WatchEvent
import androidx.compose.ui.unit.dp as dp1

@Composable
fun OfflineNetworkStatus(){
    Box(
        modifier = Modifier
            .background(Color.Red)
            .animateContentSize()
            .padding(20.dp)
            .fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()){
            Icon(painter = painterResource(R.drawable.internet_off),
                contentDescription ="internet off",
                tint = Color.White,
                modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text(text = "No Internet Connection!", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(images: List<Pair<String,String>>,
                    imageUrl:String,
                    onRefresh:()->Unit,
                    isRefreshing:Boolean,
                    isOffline: Boolean,
                    onClick: (String) -> Unit) {
    if(isOffline){
        OfflineNetworkStatus()
    }
    if(images.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize() // Fill the available space
        ) {
            GradientButton(
                onClick = onRefresh,
                modifier = Modifier.align(Alignment.Center),
                gradient = Brush.horizontalGradient(colors = listOf(color1, color2))
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reload"
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Reload", color = Color.White)
                }
            }
        }
    } else {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(contentPadding = PaddingValues(10.dp1)) {
                    items(items = images) { item ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .clickable { onClick(item.second) }
                                    .padding(vertical = 8.dp1)) {
                                Text(
                                    text = item.first,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        LoadImage(imageUrl, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}
@Composable
fun LoadImage(
    model: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = model,
        loading = {
            CircularProgressIndicator(modifier = Modifier.requiredSize(40.dp1))
        },
        contentDescription = null,
    )
}

@PreviewScreenSizes
@Composable
fun PreviewListScreen(){
    var imgUrl by remember { mutableStateOf("https://photographylife.com/wp-content/uploads/2023/05/Nikon-Z8-Official-Samples-00001.jpg") }
    ImageListScreen(images = emptyList(),
                    imageUrl = imgUrl,
                    isRefreshing = false,
                    onRefresh = {},
                    isOffline = true,
                    onClick = { imgUrl = it })
}
