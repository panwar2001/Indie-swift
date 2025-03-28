package com.panwar2001.myapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import com.panwar2001.myapplication.database.MedicineEntity
import androidx.compose.ui.unit.dp as dp1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(
    images: List<MedicineEntity>,
    imageUrl: String,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    isOffline: Boolean,
    onClick: (String) -> Unit
) {
    if(images.isEmpty()){
        NetworkOffScreen(isOffline)
    }
     else {
         Row(modifier = Modifier.fillMaxSize()){
             LazyColumn(contentPadding = PaddingValues(10.dp1)) {
                 items(items = images) { item ->
                     Column {
                         Row(
                             modifier = Modifier
                                 .clickable { onClick(item.imgFileName) }
                                 .padding(vertical = 8.dp1)) {
                             Text(
                                 text = item.medicine,
                                 overflow = TextOverflow.Ellipsis,
                                 fontSize = 12.sp
                             )
                         }
                     }
                 }
             }
             Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                 LoadImage(imageUrl, modifier = Modifier.fillMaxWidth())
             }
         }
    }
}

@Composable
fun LoadImage(
    model: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier // Image will fill the width while preserving aspect ratio
            .wrapContentHeight() // Allow the image to take only as much vertical space as needed
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
}

@PreviewScreenSizes
@Composable
fun PreviewListScreen() {
    var imgUrl by remember { mutableStateOf("https://photographylife.com/wp-content/uploads/2023/05/Nikon-Z8-Official-Samples-00001.jpg") }
    ImageListScreen(
        images = emptyList(),
        imageUrl = imgUrl,
        isRefreshing = true,
        onRefresh = {},
        isOffline = false,
        onClick = { imgUrl })
}
