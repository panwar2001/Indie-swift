package com.panwar2001.myapplication

sealed class Screens(val route : String) {
    object Index1Screen: Screens("index1")
    object Index2Screen: Screens("index2")
}
