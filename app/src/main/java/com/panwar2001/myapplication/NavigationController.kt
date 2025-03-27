package com.panwar2001.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


/**
 * Composable that has navigation host and graph for navigating among different composable screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationController(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel = hiltViewModel<AppViewModel>()
) {
    NavHost(navController = navController, startDestination = Screens.Index2Screen.route) {
        composable(route = Screens.Index1Screen.route) {
            val uiState by viewModel.uiState.collectAsState()
            val index1Data by viewModel.arrayList.collectAsState()
            val isNetworkAvailable by viewModel.networkStatus.observeAsState(initial = false)
            Scaffold { paddingValue ->
                Column(modifier = Modifier.padding(paddingValue)) {
                    ImageListScreen(
                        images = index1Data,
                        imageUrl = uiState.imgUrl,
                        onRefresh = viewModel::loadMetaData,
                        isRefreshing = uiState.loading,
                        isOffline = isNetworkAvailable,
                        onClick = viewModel::setCurrentImg
                    )
                }
            }
        }
        composable(route = Screens.Index2Screen.route) {
            val uiState by viewModel.uiState.collectAsState()
            val index2Data by viewModel.arrayList.collectAsState()
            val isNetworkAvailable by viewModel.networkStatus.observeAsState(initial = false)
            Scaffold { paddingValue ->
                Column(modifier = Modifier.padding(paddingValue)) {
                    ImageListScreen(
                        images = index2Data,
                        imageUrl = uiState.imgUrl,
                        onRefresh = viewModel::loadMetaData,
                        isRefreshing = uiState.loading,
                        isOffline = !isNetworkAvailable,
                        onClick = viewModel::setCurrentImg
                    )
                }
            }
        }
    }
}
