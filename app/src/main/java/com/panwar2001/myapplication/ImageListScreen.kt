package com.panwar2001.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import androidx.compose.ui.unit.dp as dp1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(images: List<Pair<String,String>>,
                    imageUrl:String,
                    onRefresh:()->Unit,
                    isRefreshing:Boolean,
                    onClick: (String) -> Unit){
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize(),
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
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                    LoadImage(imageUrl, modifier = Modifier.fillMaxSize())
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
    ImageListScreen(images = index_1,
                    imageUrl = imgUrl,
                    isRefreshing = false,
                    onRefresh = {},
                    onClick = { imgUrl = it })
}
