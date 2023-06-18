package com.example.myapplication.koin.koinmodule

import android.content.Context
import androidx.room.Room
import com.example.myapplication.room.DBConfig
import com.example.myapplication.util.Constant

object RoomModule {
    fun provideRoom(context: Context) : DBConfig {
        return Room.databaseBuilder(context, DBConfig::class.java, Constant.ROOM_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}