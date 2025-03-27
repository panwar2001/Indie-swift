package com.panwar2001.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetworkObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : LiveData<Boolean>(){

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Store the NetworkCallback so that we can unregister it later
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(true)  // Network is available, set LiveData to true
        }

        override fun onLost(network: Network) {
            postValue(false)  // Network is lost, set LiveData to false
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
