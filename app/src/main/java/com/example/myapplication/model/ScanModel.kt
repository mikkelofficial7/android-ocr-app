package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_scan_data")
class ScanModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var value: String = ""
)