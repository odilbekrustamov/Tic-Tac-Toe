package com.exsample.tictactoe.fragments.singleplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exsample.tictactoe.model.GameResults
import com.exsample.tictactoe.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SinglePlayerViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {

    val isUser = MutableLiveData<Boolean>()
    var getItem = MutableLiveData<GameResults>()

    fun insertGameResultsToDB(gameResults: GameResults){
        viewModelScope.launch {
            repository.insertGameResultsToDB(gameResults)
        }
    }

    fun isRowIsExist(yourName: String){
        viewModelScope.launch {
            val items = repository.isRowIsExist(yourName)
            isUser.postValue(items)
        }
    }

    fun getItem(yourName: String){
        viewModelScope.launch {
            val item = repository.getItem(yourName)
            getItem.postValue(item)
        }
    }

}