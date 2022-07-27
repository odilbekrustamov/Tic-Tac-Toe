package com.exsample.tictactoe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.exsample.tictactoe.model.GameResults

@Database(entities = [GameResults::class], version = 3)
abstract class AppDtabase: RoomDatabase() {

    abstract fun getGameResultsDao(): GameResultsDao

    companion object{
        private var DB_INSTANCE: AppDtabase? = null

        fun getAppDBInstance(context: Context): AppDtabase{
            if (DB_INSTANCE == null){
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDtabase::class.java,
                    "DB_Game_Results"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }

}