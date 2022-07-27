package com.exsample.tictactoe.fragments.gameresults

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exsample.tictactoe.model.GameResults
import com.exsample.tictactoe.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameResultsViewModel
@Inject constructor(
    private val repository: MainRepository
    ): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val getGamesResultsFromDB = MutableLiveData<List<GameResults>>()

    fun getGamesResultsFromDB(){
        viewModelScope.launch {
            val items = repository.getGamesResultsFromDB()
            getGamesResultsFromDB.postValue(items)
        }
    }

}