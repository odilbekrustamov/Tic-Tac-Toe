package com.exsample.tictactoe.di

import android.app.Application
import com.exsample.tictactoe.db.AppDtabase
import com.exsample.tictactoe.db.GameResultsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Room Related
     */


    @Provides
    @Singleton
    fun appDatabase(contect: Application): AppDtabase {
        return  AppDtabase.getAppDBInstance(contect)
    }

    @Provides
    @Singleton
    fun tvShowDao(appDtabase: AppDtabase): GameResultsDao{
        return appDtabase.getGameResultsDao()
    }

}