package com.panwar2001.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.panwar2001.myapplication.AppViewModel


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
            val isNetworkAvailable by viewModel.networkAvailable.observeAsState(initial = false)
            Scaffold { paddingValue ->
                Column(modifier = Modifier.padding(paddingValue)) {
                    ImageListScreen(
                        images = index1Data,
                        imageUrl = uiState.imgUrl,
                        isOffline = isNetworkAvailable,
                        onClick = viewModel::setCurrentImg
                    )
                }
            }
        }
        composable(route = Screens.Index2Screen.route) {
            val uiState by viewModel.uiState.collectAsState()
            val index2Data by viewModel.arrayList.collectAsState()
            val isNetworkAvailable by viewModel.networkAvailable.observeAsState(initial = false)
            val snackBarHostState = remember { SnackbarHostState() }
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = { NavigationDrawerContent(viewModel::loadMetaData, uiState.loading, isNetworkAvailable) }) {
                Scaffold(snackbarHost = { snackBarHostState }) { paddingValue ->
                    Column(modifier = Modifier.padding(paddingValue)) {
                        ImageListScreen(
                            images = index2Data,
                            imageUrl = uiState.imgUrl,
                            isOffline = !isNetworkAvailable,
                            onClick = viewModel::setCurrentImg
                        )
                    }
                }
            }
        }
    }
}
