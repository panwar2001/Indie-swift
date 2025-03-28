package com.panwar2001.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MedicineEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract  fun appDao(): AppDao
}