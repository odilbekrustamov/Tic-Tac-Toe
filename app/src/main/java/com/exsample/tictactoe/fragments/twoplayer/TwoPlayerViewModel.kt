package com.exsample.tictactoe.fragments.twoplayer

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exsample.tictactoe.model.GameResults
import com.exsample.tictactoe.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwoPlayerViewModel @Inject constructor(private var repository: MainRepository): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val isUser = MutableLiveData<Boolean>()


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

}