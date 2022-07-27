package com.exsample.tictactoe.db

import androidx.room.*
import com.exsample.tictactoe.model.GameResults


@Dao
interface GameResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameResultsToDB(gameResults: GameResults)

    @Query("SELECT * FROM game_results_table")
    suspend fun getGamesResultsFromDB(): List<GameResults>

    @Query("SELECT * FROM game_results_table WHERE yourName = :yourName")
    suspend fun getItem(yourName: String): GameResults

    @Query("SELECT EXISTS(SELECT * FROM game_results_table WHERE yourName = :yourName)")
    suspend fun isRowIsExist(yourName: String) : Boolean
}