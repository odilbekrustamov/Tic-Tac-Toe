package com.exsample.tictactoe.repository

import com.exsample.tictactoe.db.GameResultsDao
import com.exsample.tictactoe.model.GameResults
import javax.inject.Inject

class MainRepository @Inject constructor(private val gameResultsDao: GameResultsDao) {

    /**
     * Room Related
     */


    suspend fun getGamesResultsFromDB() = gameResultsDao.getGamesResultsFromDB()
    suspend fun isRowIsExist(yourName: String) = gameResultsDao.isRowIsExist(yourName)
    suspend fun insertGameResultsToDB(gameResults: GameResults) = gameResultsDao.insertGameResultsToDB(gameResults)
    suspend fun getItem(yourName: String) = gameResultsDao.getItem(yourName)

}