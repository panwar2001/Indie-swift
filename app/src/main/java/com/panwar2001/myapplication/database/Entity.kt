package com.panwar2001.myapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine")
data class MedicineEntity(
    @PrimaryKey val imgFileName: String,
    val medicine: String,
    val imgDownloadPath: String
)