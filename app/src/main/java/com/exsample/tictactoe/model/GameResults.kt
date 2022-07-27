package com.exsample.tictactoe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_results_table" )
class GameResults(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val tableId: Int,
    @ColumnInfo(name = "meResult") var meResult: Int = 0,
    @ColumnInfo(name = "youResult") var youResult: Int = 0,
    @ColumnInfo(name = "yourName") val yourName: String? = null,
    @ColumnInfo(name = "meName") val meName: String? = null
)