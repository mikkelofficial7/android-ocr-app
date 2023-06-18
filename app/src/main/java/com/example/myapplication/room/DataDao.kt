package com.example.myapplication.room

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.base.BaseDao
import com.example.myapplication.model.ScanModel

@Dao
interface DataDao : BaseDao<ScanModel> {
    @Query("select * from table_scan_data")
    fun getAllScanData(): List<ScanModel>
}