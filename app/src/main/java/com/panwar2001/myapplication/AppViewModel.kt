package com.panwar2001.myapplication

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panwar2001.myapplication.core.DataRepository
import com.panwar2001.myapplication.ui.indieDataUrl1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val imgUrl: String="",
    val loading: Boolean=false
)
private const val STOP_TIMEOUT_MILLISECONDS: Long = 1_000


@HiltViewModel
class AppViewModel @Inject
constructor(private val dataRepository: DataRepository,
            private val connectivityManager: ConnectivityManager): ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val _networkAvailable = MutableLiveData<Boolean>()
    val networkAvailable: LiveData<Boolean> get() = _networkAvailable
    val arrayList = dataRepository
        .arrayList
        .onEach {
            _uiState.update { it.copy(loading=false)}
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLISECONDS),
            initialValue = emptyList()
        )
    // Store the NetworkCallback so that we can unregister it later
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _networkAvailable.postValue(true)  // Network is available, set LiveData to true
            viewModelScope.launch{
                dataRepository.loadDataIfEmptyDB(indieDataUrl1)
            }
        }

        override fun onLost(network: Network) {
            _networkAvailable.postValue(false)  // Network is lost, set LiveData to false
        }
    }

    init {
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
        viewModelScope.launch {
            dataRepository.loadMetaDataFromDb()
        }
    }
    fun setCurrentImg(imgName:String){
        _uiState.update {
            it.copy(imgUrl = getUrl(imgName))
        }
    }
    fun loadMetaData(){
        _uiState.update { it.copy(loading=true)}
        viewModelScope.launch {
            dataRepository.loadNewMetaData(indieDataUrl1)
            _uiState.update { it.copy(loading=false)}
        }
    }
    private fun getUrl(photoName:String): String{
        return "https://panwar2001.github.io/indie-app-photos/drawable/${photoName}"
    }

    /**
     * Don't forget to unregister the callback when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}