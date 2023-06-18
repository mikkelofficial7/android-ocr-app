package com.example.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.ScanModel

@Database(entities = [ScanModel::class], version = 1, exportSchema = false)
abstract class DBConfig : RoomDatabase() {
    abstract fun dataDao(): DataDao
}