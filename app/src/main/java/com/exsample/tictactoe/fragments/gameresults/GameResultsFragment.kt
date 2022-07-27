package com.exsample.tictactoe.fragments.gameresults

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.exsample.tictactoe.R
import com.exsample.tictactoe.databinding.FragmentGameResultsBinding
import com.exsample.tictactoe.fragments.BaseFragment

class GameResultsFragment : BaseFragment(R.layout.fragment_game_results) {
    private val viewModel: GameResultsViewModel by viewModels()
    private lateinit var binding: FragmentGameResultsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentGameResultsBinding.bind(view)
        initViews()
    }


    private fun initViews() {

        viewModel.getGamesResultsFromDB()


        initObserves()
    }

    private fun initObserves() {
        // Room Related
        viewModel.getGamesResultsFromDB.observe(requireActivity(), {
            Log.d("TAG", "initObserves: ${it}")
        })


    }
}