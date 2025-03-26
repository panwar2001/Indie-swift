package com.panwar2001.myapplication
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton


interface DataRepoInterface {
    val arrayList: StateFlow<List<Pair<String, String>>>
    suspend fun loadMetaData(fileUrl: String)
}

@Singleton
class DataRepository @Inject constructor(): DataRepoInterface {
    private val _progress = MutableStateFlow<List<Pair<String, String>>>(emptyList())

    override val arrayList: StateFlow<List<Pair<String, String>>> get() = _progress

    override suspend fun loadMetaData(fileUrl: String) {
        Log.e("url received: ", fileUrl)
        try {
            withContext(Dispatchers.IO) {
                val url = URL(fileUrl)
                val connection = url.openConnection()
                connection.connect()
                val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
                reader.use {
                    _progress.value = it.lineSequence()
                        .map { line ->
                            val data = line.split(",", limit = 2)
                            data[0] to data[1]
                        }
                        .toList()
                    Log.e("df", _progress.value.toString())
                }
            }
        }catch (e: Exception){
            Log.e("error",e.message.toString())
            _progress.value= emptyList()
        }
    }
}
