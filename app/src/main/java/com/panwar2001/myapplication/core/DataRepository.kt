package com.panwar2001.myapplication.core
import android.util.Log
import com.panwar2001.myapplication.database.AppDao
import com.panwar2001.myapplication.database.MedicineEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton


interface DataRepoInterface {
    val arrayList: StateFlow<List<MedicineEntity>>
    suspend fun loadNewMetaData(fileUrl: String)
    suspend fun loadMetaDataFromDb()
    suspend fun loadDataIfEmptyDB(fileUrl: String)
}

@Singleton
class DataRepository @Inject constructor(private val appDao: AppDao): DataRepoInterface {
    private val _progress = MutableStateFlow<List<MedicineEntity>>(emptyList())

    override val arrayList: StateFlow<List<MedicineEntity>> get() = _progress

    override suspend fun loadNewMetaData(fileUrl: String) {
        Log.e("url received: ", fileUrl)
        try {
            withContext(Dispatchers.IO) {
                val url = URL(fileUrl)
                val connection = url.openConnection()
                connection.connect()
                val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
                appDao.deleteAllMedicines()
                reader.use {
                    _progress.value = it.lineSequence()
                        .map { line ->
                            val data = line.split(",", limit = 2)
                            CoroutineScope(Dispatchers.Main).launch {
                            appDao.insertMedicine(medicine = MedicineEntity(data[1],data[0],""))
                            }
                            MedicineEntity(data[1],data[0],"")
                        }
                        .toList()
                }
            }
        }catch (e: Exception){
            Log.e("error",e.message.toString())
            _progress.value= emptyList()
        }
    }
    override suspend fun loadMetaDataFromDb(){
        try {
            withContext(Dispatchers.IO) {
                appDao.retrieveAllMedicines().collect { medicineList ->
                    _progress.value= medicineList
                }
            }
        }catch (e: Exception){
            Log.e("error",e.message.toString())
            _progress.value= emptyList()
        }
    }

    override suspend fun loadDataIfEmptyDB(fileUrl: String) {
        if(appDao.countRows()==0){
            loadNewMetaData(fileUrl)
        }
    }
}
