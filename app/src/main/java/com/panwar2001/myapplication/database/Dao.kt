package com.panwar2001.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedicine(medicine : MedicineEntity): Long

    @Query("select * from medicine;")
    fun retrieveAllMedicines(): Flow<List<MedicineEntity>>

    @Query("delete from medicine;")
    suspend fun deleteAllMedicines()

    @Query("SELECT COUNT(*) FROM medicine")
    suspend fun countRows(): Int
}