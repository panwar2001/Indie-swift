package com.panwar2001.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            networkObserver: NetworkObserver): ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val networkStatus: LiveData<Boolean> = networkObserver
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

    init {
        loadMetaData()
    }
    fun setCurrentImg(imgName:String){
        _uiState.update {
            it.copy(imgUrl = getUrl("/drawable",imgName))
        }
    }
    fun loadMetaData(){
        _uiState.update { it.copy(loading=true)}
        viewModelScope.launch {
            val url = getUrl(photoName = "indie-data-1.txt")
            dataRepository.loadMetaData(url)
            _uiState.update { it.copy(loading=false)}
        }
    }
    private fun getUrl(folder:String="",photoName:String): String{
        return "https://panwar2001.github.io/indie-app-photos${folder}/${photoName}"
    }
}